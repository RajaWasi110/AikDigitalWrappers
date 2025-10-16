package com.aik.aikdigitalwrappers.service;

import com.aik.aikdigitalwrappers.dto.soap.requests.VerifyAccountRequest;
import com.aik.aikdigitalwrappers.dto.soap.responses.VerifyAccountResponse;
import com.aik.aikdigitalwrappers.exception.ExternalServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
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

    private static final String USERNAME = "ApiGee@JS";
    private static final String PASSWORD = "ApiGee@JS";
    private static final String TRANSACTION_TYPE = "01";
    private static final String CHANNEL_ID = "APIGEE";
    private static final String RESERVED1 = "01";

    public VerifyAccountService(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public VerifyAccountResponse verifyAccountUat(VerifyAccountRequest request) {
        return sendVerifyAccountRequest(request, uatUrl, "UAT");
    }

    public VerifyAccountResponse verifyAccountProd(VerifyAccountRequest request) {
        return sendVerifyAccountRequest(request, prodUrl, "PROD");
    }

    private VerifyAccountResponse sendVerifyAccountRequest(VerifyAccountRequest request, String url, String env) {
        long startTime = System.currentTimeMillis();
        log.info("[{}] Starting VerifyAccount SOAP request", env);
        log.debug("[{}] Request DTO: {}", env, request);

        try {
            String soapBody = buildSoapEnvelope(request);
            log.trace("[{}] SOAP Request XML:\n{}", env, soapBody);

            String xmlResponse = sendSourceAndReceiveAsString(url, soapBody);
            log.trace("[{}] SOAP Response XML:\n{}", env, xmlResponse);

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
            throw new ExternalServiceException(env + " SOAP VerifyAccount API failed", 500, ex.getMessage());

        } finally {
            log.debug("[{}] VerifyAccount SOAP request finished in {} ms", env,
                    (System.currentTimeMillis() - startTime));
        }
    }

    private String sendSourceAndReceiveAsString(String url, String soapBody) {
        try {
            StringSource requestSource = new StringSource(soapBody);
            StringWriter writer = new StringWriter();

            webServiceTemplate.sendSourceAndReceiveToResult(
                    url,
                    requestSource,
                    new javax.xml.transform.stream.StreamResult(writer)
            );

            return writer.toString();
        } catch (Exception e) {
            log.error("SOAP send/receive failed for URL {}: {}", url, e.getMessage(), e);
            throw new ExternalServiceException("SOAP send/receive failed", 500, e.getMessage());
        }
    }

    private String buildSoapEnvelope(VerifyAccountRequest request) {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<tem:verifyAccountRequest>" +
                "<UserName>" + USERNAME + "</UserName>" +
                "<Password>" + PASSWORD + "</Password>" +
                "<Cnic>" + request.getCnic() + "</Cnic>" +
                "<DateTime>" + request.getDateTime() + "</DateTime>" +
                "<MobileNumber>" + request.getMobileNumber() + "</MobileNumber>" +
                "<Rrn>" + request.getRrn() + "</Rrn>" +
                "<TransactionType>" + TRANSACTION_TYPE + "</TransactionType>" +
                "<ChannelId>" + CHANNEL_ID + "</ChannelId>" +
                "<Reserved1>" + RESERVED1 + "</Reserved1>" +
                "<Reserved2>" + safeValue(request.getReserved2()) + "</Reserved2>" +
                "<Reserved3>" + safeValue(request.getReserved3()) + "</Reserved3>" +
                "<Reserved4>" + safeValue(request.getReserved4()) + "</Reserved4>" +
                "<Reserved5>" + safeValue(request.getReserved5()) + "</Reserved5>" +
                "<HashData>" + request.getHashData() + "</HashData>" +
                "</tem:verifyAccountRequest>" +
                "</soapenv:Body></soapenv:Envelope>";
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
            NodeList responseNodes = root.getElementsByTagName("ns2:verifyAccountResponse");
            if (responseNodes.getLength() == 0)
                responseNodes = root.getElementsByTagName("verifyAccountResponse");

            if (responseNodes.getLength() == 0) {
                log.warn("SOAP response does not contain expected verifyAccountResponse node");
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

        } catch (ExternalServiceException e) {
            throw e;
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
