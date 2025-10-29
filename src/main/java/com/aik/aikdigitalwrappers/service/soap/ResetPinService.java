package com.aik.aikdigitalwrappers.service.soap;

import com.aik.aikdigitalwrappers.dto.soap.requests.ResetPinRequest;
import com.aik.aikdigitalwrappers.dto.soap.requests.ResetPinSoapRequest;
import com.aik.aikdigitalwrappers.dto.soap.responses.ResetPinResponse;
import com.aik.aikdigitalwrappers.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResetPinService {

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

    // ---------- Public Endpoints ----------
    public ResetPinResponse resetPinUat(ResetPinRequest request) {
        ResetPinSoapRequest resetPinSoapRequest = new ResetPinSoapRequest();
        resetPinSoapRequest.setUserName(uatUsername);
        resetPinSoapRequest.setPassword(uatPassword);
        resetPinSoapRequest.setMobileNumber(request.getMobileNumber());
        resetPinSoapRequest.setDateTime(request.getDateTime());
        resetPinSoapRequest.setRrn(request.getRrn());
        resetPinSoapRequest.setChannelId(CHANNEL_ID);
        resetPinSoapRequest.setTerminalId(TERMINAL_ID);
        resetPinSoapRequest.setNewLoginPin(request.getNewLoginPin());
        resetPinSoapRequest.setConfirmLoginPin(request.getConfirmLoginPin());
        resetPinSoapRequest.setCnic(request.getCnic());
        resetPinSoapRequest.setReserved1(request.getReserved1());
        resetPinSoapRequest.setReserved2(request.getReserved2());
        return resetPinResponse(resetPinSoapRequest, uatUrl, "U");
    }

    public ResetPinResponse resetPinProd(ResetPinRequest request) {
        ResetPinSoapRequest resetPinSoapRequest = new ResetPinSoapRequest();
        resetPinSoapRequest.setUserName(prodUsername);
        resetPinSoapRequest.setPassword(prodPassword);
        resetPinSoapRequest.setMobileNumber(request.getMobileNumber());
        resetPinSoapRequest.setDateTime(request.getDateTime());
        resetPinSoapRequest.setRrn(request.getRrn());
        resetPinSoapRequest.setChannelId(CHANNEL_ID);
        resetPinSoapRequest.setTerminalId(TERMINAL_ID);
        resetPinSoapRequest.setNewLoginPin(request.getNewLoginPin());
        resetPinSoapRequest.setConfirmLoginPin(request.getConfirmLoginPin());
        resetPinSoapRequest.setCnic(request.getCnic());
        resetPinSoapRequest.setReserved1(request.getReserved1());
        resetPinSoapRequest.setReserved2(request.getReserved2());
        return resetPinResponse(resetPinSoapRequest, prodUrl, "P");
    }

    // ---------- Core SOAP Logic ----------
    public ResetPinResponse resetPinResponse(ResetPinSoapRequest resetPinRequest, String url, String env) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(resetPinRequest.getUserName())
                .append(resetPinRequest.getPassword())
                .append(resetPinRequest.getMobileNumber())
                .append(resetPinRequest.getDateTime())
                .append(resetPinRequest.getRrn())
                .append(resetPinRequest.getChannelId())
                .append(resetPinRequest.getTerminalId())
                .append(resetPinRequest.getNewLoginPin())
                .append(resetPinRequest.getConfirmLoginPin())
                .append(resetPinRequest.getCnic())
                .append(resetPinRequest.getReserved1())
                .append(resetPinRequest.getReserved2());

        String hashData = DigestUtils.sha256Hex(stringBuilder.toString());

        StringBuilder requestStringBuilder = new StringBuilder("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <tem:ResetPinRequest>\n" +
                "         <UserName>" + resetPinRequest.getUserName() + "</UserName>\n" +
                "         <Password>" + resetPinRequest.getPassword() + "</Password>\n" +
                "         <MobileNumber>" + resetPinRequest.getMobileNumber() + "</MobileNumber>\n" +
                "         <DateTime>" + resetPinRequest.getDateTime() + "</DateTime>\n" +
                "         <Rrn>" + resetPinRequest.getRrn() + "</Rrn>\n" +
                "         <ChannelId>" + resetPinRequest.getChannelId() + "</ChannelId>\n" +
                "         <TerminalId>" + resetPinRequest.getTerminalId() + "</TerminalId>\n" +
                "         <NewLoginPin>" + resetPinRequest.getNewLoginPin() + "</NewLoginPin>\n" +
                "         <ConfirmLoginPin>" + resetPinRequest.getConfirmLoginPin() + "</ConfirmLoginPin>\n" +
                "         <CNIC>" + resetPinRequest.getCnic() + "</CNIC>\n" +
                "         <Reserved1>" + resetPinRequest.getReserved1() + "</Reserved1>\n" +
                "         <Reserved2>" + resetPinRequest.getReserved2() + "</Reserved2>\n" +
                "         <HashData>" + hashData + "</HashData>\n" +
                "      </tem:ResetPinRequest>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>");

        ResetPinResponse resetPinResponse = new ResetPinResponse();

        try {
            JSONObject jObject = Util.getSoapResponseFromDebitWsdl(url, requestStringBuilder.toString());
            if (jObject != null) {
                jObject = jObject.getJSONObject("soap:Envelope");
                jObject = jObject.getJSONObject("soap:Body");
                jObject = jObject.getJSONObject("ns2:resetPinResponse");

                String responseCode = jObject.has("ResponseCode") ? jObject.get("ResponseCode").toString() : null;
                if (responseCode != null) {
                    if (responseCode.equals("00")) {
                        resetPinResponse.setResponseCode(responseCode);
                        resetPinResponse.setResponseDescription("Successful");
                    } else {
                        resetPinResponse.setResponseCode(responseCode);
                        resetPinResponse.setResponseDescription(
                                jObject.has("ResponseDescription")
                                        ? jObject.get("ResponseDescription").toString()
                                        : null
                        );
                    }
                } else {
                    resetPinResponse.setResponseDescription("Service not available");
                }
            } else {
                resetPinResponse.setResponseDescription("Service not available");
            }
        } catch (Exception e) {
            resetPinResponse.setResponseDescription(e.getLocalizedMessage());
        }
        return resetPinResponse;
    }
}
