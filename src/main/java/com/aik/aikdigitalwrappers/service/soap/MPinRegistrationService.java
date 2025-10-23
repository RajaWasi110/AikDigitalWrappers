package com.aik.aikdigitalwrappers.service.soap;

import com.aik.aikdigitalwrappers.dto.soap.requests.MPinRegistrationRequest;
import com.aik.aikdigitalwrappers.dto.soap.responses.MPinRegistrationResponse;
import com.aik.aikdigitalwrappers.exception.ExternalServiceException;
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
public class MPinRegistrationService {

    private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();

    // 🔹 Load from application.properties
    @Value("${uat.username}")
    private String uatUsername;
    @Value("${uat.password}")
    private String uatPassword;

    @Value("${prod.username}")
    private String prodUsername;
    @Value("${prod.password}")
    private String prodPassword;

    @Value("${mpinregistration.uat.url}")
    private String uatUrl;
    @Value("${mpinregistration.prod.url}")
    private String prodUrl;
    @Value("${mpinregistration.action}")
    private String soapAction;

    // 🔹 Hardcoded
    private static final String CHANNEL_ID = "NOVA";
    private static final String TERMINAL_ID = "NOVA";

    public MPinRegistrationResponse registerUat(MPinRegistrationRequest request) {
        return sendSoapRequest(uatUrl, uatUsername, uatPassword, request, "UAT");
    }

    public MPinRegistrationResponse registerProd(MPinRegistrationRequest request) {
        return sendSoapRequest(prodUrl, prodUsername, prodPassword, request, "PROD");
    }

    private MPinRegistrationResponse sendSoapRequest(String url, String username, String password,
                                                     MPinRegistrationRequest r, String env) {
        try {
            log.info("Sending MPINRegistration SOAP request [{}] to {}", env, url);

            String soapRequest = buildSoapEnvelope(r, username, password);
            log.debug("SOAP Request [{}]:\n{}", env, soapRequest);

            Source source = new StreamSource(new StringReader(soapRequest));
            StringWriter writer = new StringWriter();

            webServiceTemplate.sendSourceAndReceiveToResult(url, source, new StreamResult(writer));
            String soapResponse = writer.toString();
            log.debug("SOAP Response [{}]:\n{}", env, soapResponse);

            MPinRegistrationResponse response = parseSoapResponse(soapResponse);
            log.info("MPINRegistration [{}] Response: {}", env, response);
            return response;

        } catch (Exception e) {
            log.error("MPINRegistration {} API failed: {}", env, e.getMessage(), e);
            throw new ExternalServiceException("MPINRegistration " + env + " SOAP API failed", 500, e.getMessage());
        }
    }

    private String buildSoapEnvelope(MPinRegistrationRequest r, String username, String password) {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<tem:mpinRegistrationRequest>" +
                "<UserName>" + username + "</UserName>" +
                "<Password>" + password + "</Password>" +
                "<Mpin>" + r.getMpin() + "</Mpin>" +
                "<ConfirmMpin>" + r.getConfirmMpin() + "</ConfirmMpin>" +
                "<DateTime>" + r.getDateTime() + "</DateTime>" +
                "<MobileNumber>" + r.getMobileNumber() + "</MobileNumber>" +
                "<Rrn>" + r.getRrn() + "</Rrn>" +
                "<ChannelId>" + CHANNEL_ID + "</ChannelId>" +
                "<TerminalId>" + TERMINAL_ID + "</TerminalId>" +
                "<Reserved1>" + safeValue(r.getReserved1()) + "</Reserved1>" +
                "<Reserved2>" + safeValue(r.getReserved2()) + "</Reserved2>" +
                "<Reserved3>" + safeValue(r.getReserved3()) + "</Reserved3>" +
                "<Reserved4>" + safeValue(r.getReserved4()) + "</Reserved4>" +
                "<Reserved5>" + safeValue(r.getReserved5()) + "</Reserved5>" +
                "<HashData>00C4EEC3FE0EC19B3B7680E366A46F92211937845AB7251F5CD880F52B2BF74A</HashData>" +
                "</tem:mpinRegistrationRequest>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";
    }

    private String safeValue(String value) {
        return value == null ? "" : value;
    }

    private MPinRegistrationResponse parseSoapResponse(String xml) {
        String rrn = getTagValue(xml, "Rrn");
        String code = getTagValue(xml, "ResponseCode");
        String desc = getTagValue(xml, "ResponseDescription");
        String hash = getTagValue(xml, "HashData");
        return new MPinRegistrationResponse(rrn, code, desc, hash);
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
