package com.aik.aikdigitalwrappers.service.soap;

import com.aik.aikdigitalwrappers.dto.soap.requests.ResetPinRequest;
import com.aik.aikdigitalwrappers.dto.soap.responses.ResetPinResponse;
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
public class ResetPinService {

    private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();

    @Value("${resetpin.uat.url}")
    private String uatUrl;

    @Value("${resetpin.prod.url}")
    private String prodUrl;

    @Value("${uat.username}")
    private String uatUsername;

    @Value("${uat.password}")
    private String uatPassword;

    @Value("${prod.username}")
    private String prodUsername;

    @Value("${prod.password}")
    private String prodPassword;

    @Value("${resetpin.action}")
    private String soapAction;

    private static final String CHANNEL_ID = "NOVA";
    private static final String TERMINAL_ID = "NOVA";

    // ---------- PUBLIC METHODS ----------
    public ResetPinResponse resetPinUat(ResetPinRequest request) {
        return sendSoapRequest(uatUrl, uatUsername, uatPassword, request, "UAT");
    }

    public ResetPinResponse resetPinProd(ResetPinRequest request) {
        return sendSoapRequest(prodUrl, prodUsername, prodPassword, request, "PROD");
    }

    // ---------- CORE SOAP LOGIC ----------
    private ResetPinResponse sendSoapRequest(String url, String username, String password,
                                             ResetPinRequest req, String env) {
        try {
            log.info("Sending ResetPIN SOAP request [{}] to {}", env, url);

            // Combine all parameters into a single string for SHA-256 hashing
            String hashInput = username + password +
                    safeValue(req.getMobileNumber()) +
                    safeValue(req.getRrn()) +
                    safeValue(req.getNewLoginPin()) +
                    safeValue(req.getConfirmLoginPin()) +
                    safeValue(req.getCnic()) +
                    safeValue(req.getReserved1()) +
                    safeValue(req.getReserved2());

            String hashData = HashUtil.sha256(hashInput);

            String soapRequest = buildSoapEnvelope(username, password, req, hashData);
            log.debug("SOAP Request [{}]:\n{}", env, soapRequest);

            Source source = new StreamSource(new StringReader(soapRequest));
            StringWriter writer = new StringWriter();

            webServiceTemplate.sendSourceAndReceiveToResult(url, source, new StreamResult(writer));

            String soapResponse = writer.toString();
            log.debug("SOAP Response [{}]:\n{}", env, soapResponse);

            ResetPinResponse response = parseSoapResponse(soapResponse);
            log.info("✅ ResetPIN [{}] Response: {}", env, response);

            return response;

        } catch (Exception e) {
            log.error("ResetPIN {} API failed: {}", env, e.getMessage(), e);
            // Fixed constructor usage
            throw new ExternalServiceException("ResetPIN " + env + " SOAP API failed", e);
        }
    }

    // ---------- BUILD SOAP ENVELOPE ----------
    private String buildSoapEnvelope(String username, String password, ResetPinRequest r, String hashData) {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:tem=\"http://tempuri.org/\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<tem:ResetPinRequest>" +
                "<UserName>" + username + "</UserName>" +
                "<Password>" + password + "</Password>" +
                "<MobileNumber>" + safeValue(r.getMobileNumber()) + "</MobileNumber>" +
                "<DateTime>" + safeValue(r.getDateTime()) + "</DateTime>" +
                "<Rrn>" + safeValue(r.getRrn()) + "</Rrn>" +
                "<ChannelId>" + CHANNEL_ID + "</ChannelId>" +
                "<TerminalId>" + TERMINAL_ID + "</TerminalId>" +
                "<NewLoginPin>" + safeValue(r.getNewLoginPin()) + "</NewLoginPin>" +
                "<ConfirmLoginPin>" + safeValue(r.getConfirmLoginPin()) + "</ConfirmLoginPin>" +
                "<CNIC>" + safeValue(r.getCnic()) + "</CNIC>" +
                "<Reserved1>" + safeValue(r.getReserved1()) + "</Reserved1>" +
                "<Reserved2>" + safeValue(r.getReserved2()) + "</Reserved2>" +
                "<HashData>" + hashData + "</HashData>" +
                "</tem:ResetPinRequest>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";
    }

    private String safeValue(String value) {
        return (value == null) ? "" : value;
    }

    // ---------- PARSE SOAP RESPONSE ----------
    private ResetPinResponse parseSoapResponse(String xml) {
        String rrn = getTagValue(xml, "Rrn");
        String code = getTagValue(xml, "ResponseCode");
        String desc = getTagValue(xml, "ResponseDescription");
        String datetime = getTagValue(xml, "ResponseDateTime");
        String hash = getTagValue(xml, "HashData");
        return new ResetPinResponse(rrn, code, desc, datetime, hash);
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
