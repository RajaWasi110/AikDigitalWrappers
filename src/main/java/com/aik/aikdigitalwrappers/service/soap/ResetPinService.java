package com.aik.aikdigitalwrappers.service.soap;

import com.aik.aikdigitalwrappers.dto.soap.requests.ResetPinRequest;
import com.aik.aikdigitalwrappers.dto.soap.responses.ResetPinResponse;
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
public class ResetPinService {

    private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();

    private static final String SOAP_ACTION = "tem:ResetPinRequest";
    private static final String USERNAME = "ApiGee@JS";
    private static final String PASSWORD = "ApiGee@JS";
    private static final String CHANNEL_ID = "NOVA";
    private static final String TERMINAL_ID = "NOVA";

    // Hardcoded URLs (UAT + PROD)
    private static final String UAT_URL = "http://10.150.0.5:7070/js-blb-integration/JsBLBIntegration";
    private static final String PROD_URL = "http://10.150.0.5:7070/js-blb-integration/JsBLBIntegration";

    public ResetPinResponse resetPinUat(ResetPinRequest request) {
        return sendSoapRequest(UAT_URL, request, "UAT");
    }

    public ResetPinResponse resetPinProd(ResetPinRequest request) {
        return sendSoapRequest(PROD_URL, request, "PROD");
    }

    private ResetPinResponse sendSoapRequest(String url, ResetPinRequest req, String env) {
        try {
            log.info("▶️ Sending ResetPIN SOAP request [{}] to {}", env, url);

            String soapRequest = buildSoapEnvelope(req);
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
            log.error("❌ ResetPIN {} API failed: {}", env, e.getMessage(), e);
            throw new ExternalServiceException("ResetPIN " + env + " SOAP API failed", 500, e.getMessage());
        }
    }

    private String buildSoapEnvelope(ResetPinRequest r) {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<tem:ResetPinRequest>" +
                "<UserName>" + USERNAME + "</UserName>" +
                "<Password>" + PASSWORD + "</Password>" +
                "<MobileNumber>" + r.getMobileNumber() + "</MobileNumber>" +
                "<DateTime>" + r.getDateTime() + "</DateTime>" +
                "<Rrn>" + r.getRrn() + "</Rrn>" +
                "<ChannelId>" + CHANNEL_ID + "</ChannelId>" +
                "<TerminalId>" + TERMINAL_ID + "</TerminalId>" +
                "<NewLoginPin>" + r.getNewLoginPin() + "</NewLoginPin>" +
                "<ConfirmLoginPin>" + r.getConfirmLoginPin() + "</ConfirmLoginPin>" +
                "<CNIC>" + r.getCnic() + "</CNIC>" +
                "<Reserved1>" + safeValue(r.getReserved1()) + "</Reserved1>" +
                "<Reserved2>" + safeValue(r.getReserved2()) + "</Reserved2>" +
                "<HashData>A5D30BA001D62A5F39B1E555ECEACE2C6AABF97C6170F4F7019B4368806E696D</HashData>" +
                "</tem:ResetPinRequest>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";
    }

    private String safeValue(String value) {
        return (value == null) ? "" : value;
    }

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
