package com.aik.aikdigitalwrappers.service.soap;

import com.aik.aikdigitalwrappers.dto.soap.requests.MPinLoginRequest;
import com.aik.aikdigitalwrappers.dto.soap.responses.MPinLoginResponse;
import com.aik.aikdigitalwrappers.exception.ExternalServiceException;
import com.aik.aikdigitalwrappers.util.HashUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

@Service
@Slf4j
public class MPinLoginService {

    private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();

    @Value("${mpinlogin.uat.url}") private String UAT_URL;
    @Value("${mpinlogin.prod.url}") private String PROD_URL;
    @Value("${uat.username}") private String username;
    @Value("${uat.password}") private String password;
    @Value("${uat.secret-key:}") private String secretKey;

    private static final String SOAP_ACTION = "tem:LoginPinRequest";
    private static final String CHANNEL_ID = "NOVA";
    private static final String TERMINAL_ID = "NOVA";

    public MPinLoginResponse loginMPinUat(MPinLoginRequest request) {
        return sendSoapRequest(UAT_URL, request, "UAT");
    }

    public MPinLoginResponse loginMPinProd(MPinLoginRequest request) {
        return sendSoapRequest(PROD_URL, request, "PROD");
    }

    private MPinLoginResponse sendSoapRequest(String url, MPinLoginRequest req, String env) {
        try {
            log.info("▶️ Sending MPinLogin SOAP request [{}] to {}", env, url);

            String hashData = generateHash(req);
            String soapRequest = buildSoapEnvelope(req, hashData);
            log.debug("🧾 SOAP Request [{}]:\n{}", env, soapRequest);

            Source source = new StreamSource(new StringReader(soapRequest));
            StringWriter writer = new StringWriter();
            webServiceTemplate.sendSourceAndReceiveToResult(url, source, new StreamResult(writer));

            String soapResponse = writer.toString();
            log.debug("📥 SOAP Response [{}]:\n{}", env, soapResponse);

            MPinLoginResponse response = parseSoapResponse(soapResponse);
            log.info("✅ MPinLogin [{}] Response: {}", env, response);
            return response;

        } catch (Exception e) {
            log.error("❌ MPinLogin {} API failed: {}", env, e.getMessage(), e);
            throw new ExternalServiceException("MPinLogin " + env + " SOAP API failed", 500, e.getMessage());
        }
    }

    private String buildSoapEnvelope(MPinLoginRequest r, String hashData) {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<tem:LoginPinRequest>" +
                "<UserName>" + username + "</UserName>" +
                "<Password>" + password + "</Password>" +
                "<MobileNumber>" + r.getMobileNumber() + "</MobileNumber>" +
                "<DateTime>" + r.getDateTime() + "</DateTime>" +
                "<Rrn>" + r.getRrn() + "</Rrn>" +
                "<ChannelId>" + CHANNEL_ID + "</ChannelId>" +
                "<TerminalId>" + TERMINAL_ID + "</TerminalId>" +
                "<PIN>" + r.getPin() + "</PIN>" +
                "<Reserved1>" + safeValue(r.getReserved1()) + "</Reserved1>" +
                "<Reserved2>" + safeValue(r.getReserved2()) + "</Reserved2>" +
                "<HashData>" + hashData + "</HashData>" +
                "</tem:LoginPinRequest>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";
    }

    private String generateHash(MPinLoginRequest r) {
        // Include all parameters in the hash
        String rawData = username
                + password
                + r.getMobileNumber()
                + r.getPin()
                + r.getDateTime()
                + r.getRrn()
                + safeValue(r.getReserved1())
                + safeValue(r.getReserved2());

        if (secretKey != null && !secretKey.isEmpty()) {
            rawData += secretKey;
        }

        log.debug("🔐 Raw data for hash: {}", rawData);
        String hash = HashUtil.generateSHA256(rawData);
        log.debug("✅ Generated HashData: {}", hash);
        return hash;
    }

    private String safeValue(String value) {
        return value == null ? "" : value;
    }

    private MPinLoginResponse parseSoapResponse(String xml) {
        String rrn = getTagValue(xml, "Rrn");
        String code = getTagValue(xml, "ResponseCode");
        String desc = getTagValue(xml, "ResponseDescription");
        String datetime = getTagValue(xml, "ResponseDateTime");
        String hash = getTagValue(xml, "HashData");
        return new MPinLoginResponse(rrn, code, desc, datetime, hash);
    }

    private String getTagValue(String xml, String tag) {
        try {
            int start = xml.indexOf("<" + tag + ">") + tag.length() + 2;
            int end = xml.indexOf("</" + tag + ">");
            return (start > tag.length() && end > start) ? xml.substring(start, end) : "";
        } catch (Exception e) {
            return "";
        }
    }
}
