package com.aik.aikdigitalwrappers.service.soap;

import com.aik.aikdigitalwrappers.dto.soap.requests.MPinLoginRequest;
import com.aik.aikdigitalwrappers.dto.soap.responses.MPinLoginResponse;
import com.aik.aikdigitalwrappers.exception.ExternalServiceException;
import lombok.extern.slf4j.Slf4j;
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

    private static final String SOAP_ACTION = "tem:LoginPinRequest";

    // Hardcoded values
    private static final String USERNAME = "ApiGee@JS";
    private static final String PASSWORD = "ApiGee@JS";
    private static final String CHANNEL_ID = "NOVA";
    private static final String TERMINAL_ID = "NOVA";

    private static final String UAT_URL = "http://192.168.130.31:7070/js-blb-integration/JsBLBIntegration";
    private static final String PROD_URL = "http://192.168.130.32:7070/js-blb-integration/JsBLBIntegration";

    public MPinLoginResponse loginMPinUat(MPinLoginRequest request) {
        return sendSoapRequest(UAT_URL, request, "UAT");
    }

    public MPinLoginResponse loginMPinProd(MPinLoginRequest request) {
        return sendSoapRequest(PROD_URL, request, "PROD");
    }

    private MPinLoginResponse sendSoapRequest(String url, MPinLoginRequest req, String env) {
        try {
            log.info("▶️ Sending MPinLogin SOAP request [{}] to {}", env, url);

            String soapRequest = buildSoapEnvelope(req);
            log.debug("SOAP Request Payload [{}]:\n{}", env, soapRequest);

            Source source = new StreamSource(new StringReader(soapRequest));
            StringWriter writer = new StringWriter();

            webServiceTemplate.sendSourceAndReceiveToResult(url, source, new StreamResult(writer));

            String soapResponse = writer.toString();
            log.debug("SOAP Response [{}]:\n{}", env, soapResponse);

            MPinLoginResponse response = parseSoapResponse(soapResponse);
            log.info("✅ MPinLogin [{}] Response: {}", env, response);
            return response;

        } catch (Exception e) {
            log.error("❌ MPinLogin {} API failed: {}", env, e.getMessage(), e);
            throw new ExternalServiceException("MPinLogin " + env + " SOAP API failed", 500, e.getMessage());
        }
    }

    private String buildSoapEnvelope(MPinLoginRequest r) {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<tem:LoginPinRequest>" +
                "<UserName>" + USERNAME + "</UserName>" +
                "<Password>" + PASSWORD + "</Password>" +
                "<MobileNumber>" + r.getMobileNumber() + "</MobileNumber>" +
                "<DateTime>" + r.getDateTime() + "</DateTime>" +
                "<Rrn>" + r.getRrn() + "</Rrn>" +
                "<ChannelId>" + CHANNEL_ID + "</ChannelId>" +
                "<TerminalId>" + TERMINAL_ID + "</TerminalId>" +
                "<PIN>" + r.getPin() + "</PIN>" +
                "<Reserved1>" + safeValue(r.getReserved1()) + "</Reserved1>" +
                "<Reserved2>" + safeValue(r.getReserved2()) + "</Reserved2>" +
                "<HashData>3773696939525F33D9F56862AFB8472118FB6B7B08FE1E6A7A9A592A62E8FA58</HashData>" +
                "</tem:LoginPinRequest>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";
    }

    private String safeValue(String value) {
        return (value == null) ? "" : value;
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
