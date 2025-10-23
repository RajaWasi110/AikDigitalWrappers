package com.aik.aikdigitalwrappers.service.soap;

import com.aik.aikdigitalwrappers.dto.soap.requests.VerifyAccountRequest;
import com.aik.aikdigitalwrappers.dto.soap.responses.VerifyAccountResponse;
import com.aik.aikdigitalwrappers.exception.ExternalServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.xml.transform.StringSource;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class VerifyAccountService {

    private final WebServiceTemplate webServiceTemplate;

    @Value("${verifyaccount.uat.url}")
    private String uatUrl;

    @Value("${verifyaccount.prod.url}")
    private String prodUrl;

    @Value("${uat.username}")
    private String uatUsername;

    @Value("${uat.password}")
    private String uatPassword;

    @Value("${prod.username}")
    private String prodUsername;

    @Value("${prod.password}")
    private String prodPassword;

    private static final String TRANSACTION_TYPE = "01";
    private static final String CHANNEL_ID = "APIGEE";
    private static final String RESERVED1 = "01";
    private static final String NAMESPACE_URI = "http://tempuri.org/";
    private static final String SOAP_ACTION = "http://tempuri.org/VerifyAccount";

    public VerifyAccountService(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public VerifyAccountResponse verifyAccountUat(VerifyAccountRequest request) {
        return sendVerifyAccountRequest(request, uatUrl, uatUsername, uatPassword, "UAT");
    }

    public VerifyAccountResponse verifyAccountProd(VerifyAccountRequest request) {
        return sendVerifyAccountRequest(request, prodUrl, prodUsername, prodPassword, "PROD");
    }

    private VerifyAccountResponse sendVerifyAccountRequest(
            VerifyAccountRequest request, String url, String username, String password, String env) {

        long startTime = System.currentTimeMillis();
        log.info("[{}] Starting VerifyAccount SOAP request", env);

        try {
            String soapBody = buildSoapEnvelope(request, username, password);
            log.debug("[{}] SOAP Request:\n{}", env, soapBody);

            String xmlResponse = sendSourceAndReceiveAsString(url, soapBody);
            log.debug("[{}] SOAP Response:\n{}", env, xmlResponse);

            VerifyAccountResponse response = parseSoapResponse(xmlResponse);

            log.info("[{}] SOAP call completed | ResponseCode={} | Description={} | Duration={}ms",
                    env, response.getResponseCode(), response.getResponseDescription(),
                    (System.currentTimeMillis() - startTime));

            return response;

        } catch (ExternalServiceException e) {
            log.error("[{}] SOAP call failed: {}", env, e.getMessage(), e);
            throw e;
        } catch (Exception ex) {
            log.error("[{}] Unexpected error during SOAP request: {}", env, ex.getMessage(), ex);
            throw new ExternalServiceException(env + " VerifyAccount SOAP API failed", 500, ex.getMessage());
        } finally {
            log.debug("[{}] VerifyAccount SOAP request finished in {} ms",
                    env, (System.currentTimeMillis() - startTime));
        }
    }

    private String sendSourceAndReceiveAsString(String url, String soapBody) {
        try {
            StringSource requestSource = new StringSource(soapBody);
            StringWriter writer = new StringWriter();

            WebServiceMessageCallback callback = message -> {
                if (message instanceof SoapMessage soapMessage) {
                    soapMessage.setSoapAction(SOAP_ACTION);
                }
            };

            webServiceTemplate.sendSourceAndReceiveToResult(
                    url, requestSource, callback, new javax.xml.transform.stream.StreamResult(writer)
            );

            return writer.toString();

        } catch (Exception e) {
            log.error("SOAP send/receive failed for URL {}: {}", url, e.getMessage(), e);
            throw new ExternalServiceException("SOAP send/receive failed", 500, e.getMessage());
        }
    }

    private String buildSoapEnvelope(VerifyAccountRequest request, String username, String password) {
        return """
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                          xmlns:tem="%s">
           <soapenv:Header/>
           <soapenv:Body>
              <tem:VerifyAccountRequest>
                 <tem:UserName>%s</tem:UserName>
                 <tem:Password>%s</tem:Password>
                 <tem:Cnic>%s</tem:Cnic>
                 <tem:DateTime>%s</tem:DateTime>
                 <tem:MobileNumber>%s</tem:MobileNumber>
                 <tem:Rrn>%s</tem:Rrn>
                 <tem:TransactionType>%s</tem:TransactionType>
                 <tem:ChannelId>%s</tem:ChannelId>
                 <tem:Reserved1>%s</tem:Reserved1>
                 <tem:Reserved2>%s</tem:Reserved2>
                 <tem:Reserved3>%s</tem:Reserved3>
                 <tem:Reserved4>%s</tem:Reserved4>
                 <tem:Reserved5>%s</tem:Reserved5>
                 <tem:HashData>%s</tem:HashData>
              </tem:VerifyAccountRequest>
           </soapenv:Body>
        </soapenv:Envelope>
        """.formatted(
                NAMESPACE_URI,
                username,
                password,
                request.getCnic(),
                request.getDateTime(),
                request.getMobileNumber(),
                request.getRrn(),
                TRANSACTION_TYPE,
                CHANNEL_ID,
                RESERVED1,
                safeValue(request.getReserved2()),
                safeValue(request.getReserved3()),
                safeValue(request.getReserved4()),
                safeValue(request.getReserved5()),
                request.getHashData()
        );
    }

    private String safeValue(String value) {
        return (value == null) ? "" : value;
    }

    private VerifyAccountResponse parseSoapResponse(String xml) {
        try {
            var doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

            Element root = doc.getDocumentElement();

            NodeList responseNodes = root.getElementsByTagNameNS(NAMESPACE_URI, "VerifyAccountResponse");
            if (responseNodes.getLength() == 0)
                responseNodes = root.getElementsByTagName("VerifyAccountResponse");

            if (responseNodes.getLength() == 0) {
                throw new ExternalServiceException("Invalid SOAP response format", 500, xml);
            }

            Element response = (Element) responseNodes.item(0);
            VerifyAccountResponse dto = new VerifyAccountResponse();

            dto.setResponseCode(getTagValue(response, "ResponseCode"));
            dto.setResponseDescription(getTagValue(response, "ResponseDescription"));
            dto.setMobileNumber(getTagValue(response, "MobileNumber"));
            dto.setCnic(getTagValue(response, "Cnic"));
            dto.setRrn(getTagValue(response, "Rrn"));
            dto.setHashData(getTagValue(response, "HashData"));

            return dto;

        } catch (Exception e) {
            log.error("Error parsing SOAP response: {}", e.getMessage(), e);
            throw new ExternalServiceException("Failed to parse SOAP response", 500, e.getMessage());
        }
    }

    private String getTagValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }
}
