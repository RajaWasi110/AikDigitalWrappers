package com.aik.aikdigitalwrappers.service.soap;

import com.aik.aikdigitalwrappers.dto.soap.requests.VerifyAccountRequest;
import com.aik.aikdigitalwrappers.dto.soap.responses.VerifyAccountResponse;
import com.aik.aikdigitalwrappers.exception.ExternalServiceException;
import com.aik.aikdigitalwrappers.util.HashUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class VerifyAccountService {

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

    @Value("${verifyaccount.action}")
    private String soapAction;

    private static final String TRANSACTION_TYPE = "01";
    private static final String CHANNEL_ID = "APIGEE";
    private static final String RESERVED1 = "01";

    // ---------- Public Endpoints ----------
    public VerifyAccountResponse verifyAccountUat(VerifyAccountRequest request) {
        return sendSoapRequest(uatUrl, uatUsername, uatPassword, request, "UAT");
    }

    public VerifyAccountResponse verifyAccountProd(VerifyAccountRequest request) {
        return sendSoapRequest(prodUrl, prodUsername, prodPassword, request, "PROD");
    }

    // ---------- Core SOAP Logic ----------
    private VerifyAccountResponse sendSoapRequest(
            String url, String username, String password, VerifyAccountRequest req, String env) {

        try {
            log.info("Sending VerifyAccount SOAP request [{}] to {}", env, url);

            // Generate hash using centralized utility
            String hashInput = username + password + req.getCnic() + req.getDateTime()
                    + req.getMobileNumber() + req.getRrn();
            String hashData = HashUtil.sha256(hashInput);

            String soapRequest = buildSoapEnvelope(username, password, req, hashData);
            log.debug("SOAP Request [{}]:\n{}", env, soapRequest);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_XML);
            headers.add("SOAPAction", soapAction);

            HttpEntity<String> entity = new HttpEntity<>(soapRequest, headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            String rawXml = response.getBody();

            if (rawXml == null || rawXml.isEmpty()) {
                throw new ExternalServiceException("Empty SOAP response from " + env, 502, null);
            }

            VerifyAccountResponse parsedResponse = parseResponse(rawXml);
            log.info("VerifyAccount [{}] Response: {}", env, parsedResponse);
            return parsedResponse;

        } catch (Exception ex) {
            log.error("VerifyAccount {} API failed: {}", env, ex.getMessage(), ex);
            throw new ExternalServiceException("VerifyAccount " + env + " SOAP API failed", ex);
        }
    }

    // ---------- Build SOAP Envelope ----------
    private String buildSoapEnvelope(String username, String password, VerifyAccountRequest r, String hashData) {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:tem=\"http://tempuri.org/\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<tem:verifyAccountRequest>" +
                "<UserName>" + username + "</UserName>" +
                "<Password>" + password + "</Password>" +
                "<Cnic>" + r.getCnic() + "</Cnic>" +
                "<DateTime>" + r.getDateTime() + "</DateTime>" +
                "<MobileNumber>" + r.getMobileNumber() + "</MobileNumber>" +
                "<Rrn>" + r.getRrn() + "</Rrn>" +
                "<TransactionType>" + TRANSACTION_TYPE + "</TransactionType>" +
                "<ChannelId>" + CHANNEL_ID + "</ChannelId>" +
                "<Reserved1>" + RESERVED1 + "</Reserved1>" +
                "<Reserved2>" + nvl(r.getReserved2()) + "</Reserved2>" +
                "<Reserved3>" + nvl(r.getReserved3()) + "</Reserved3>" +
                "<Reserved4>" + nvl(r.getReserved4()) + "</Reserved4>" +
                "<Reserved5>" + nvl(r.getReserved5()) + "</Reserved5>" +
                "<HashData>" + hashData + "</HashData>" +
                "</tem:verifyAccountRequest>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";
    }

    private String nvl(String value) {
        return value == null ? "" : value;
    }

    private VerifyAccountResponse parseResponse(String xml) {
        try {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new java.io.ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

            String rrn = getTagValue(doc, "Rrn");
            String responseCode = getTagValue(doc, "ResponseCode");
            String responseDesc = getTagValue(doc, "ResponseDescription");
            String mobile = getTagValue(doc, "MobileNumber");
            String cnic = getTagValue(doc, "Cnic");
            String hash = getTagValue(doc, "HashData");

            return new VerifyAccountResponse(rrn, responseCode, responseDesc, mobile, cnic, hash, xml);
        } catch (Exception e) {
            log.error("Failed to parse VerifyAccount SOAP response: {}", e.getMessage(), e);
            throw new ExternalServiceException("Failed to parse VerifyAccount SOAP response", e);
        }
    }

    private String getTagValue(Document doc, String tag) {
        if (doc.getElementsByTagName(tag).getLength() == 0) return null;
        return doc.getElementsByTagName(tag).item(0).getTextContent();
    }
}
