package com.aik.aikdigitalwrappers.service.soap;

import com.aik.aikdigitalwrappers.dto.soap.requests.MPinRegistrationRequest;
import com.aik.aikdigitalwrappers.dto.soap.requests.MPinRegistrationSoapRequest;
import com.aik.aikdigitalwrappers.dto.soap.responses.MPinRegistrationResponse;
import com.aik.aikdigitalwrappers.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MPinRegistrationService {

    @Value("${mpinregistration.uat.url}")
    private String uatUrl;

    @Value("${mpinregistration.prod.url}")
    private String prodUrl;

    @Value("${uat.username}")
    private String uatUsername;

    @Value("${uat.password}")
    private String uatPassword;

    @Value("${prod.username}")
    private String prodUsername;

    @Value("${prod.password}")
    private String prodPassword;

    @Value("${mpinregistration.action}")
    private String soapAction;

    private static final String CHANNEL_ID = "NOVA";
    private static final String TERMINAL_ID = "NOVA";

    // ---------- Public Endpoints ----------
    public MPinRegistrationResponse mpinRegistrationUat(MPinRegistrationRequest request) {
        MPinRegistrationSoapRequest mpinSoapRequest = new MPinRegistrationSoapRequest();
        mpinSoapRequest.setUserName(uatUsername);
        mpinSoapRequest.setPassword(uatPassword);
        mpinSoapRequest.setMpin(request.getMpin());
        mpinSoapRequest.setConfirmMpin(request.getConfirmMpin());
        mpinSoapRequest.setDateTime(request.getDateTime());
        mpinSoapRequest.setMobileNumber(request.getMobileNumber());
        mpinSoapRequest.setRrn(request.getRrn());
        mpinSoapRequest.setChannelId(CHANNEL_ID);
        mpinSoapRequest.setTerminalId(TERMINAL_ID);
        mpinSoapRequest.setReserved1(request.getReserved1());
        mpinSoapRequest.setReserved2(request.getReserved2());
        mpinSoapRequest.setReserved3(request.getReserved3());
        mpinSoapRequest.setReserved4(request.getReserved4());
        mpinSoapRequest.setReserved5(request.getReserved5());
        return mpinRegistrationResponse(mpinSoapRequest, uatUrl, "U");
    }

    public MPinRegistrationResponse mpinRegistrationProd(MPinRegistrationRequest request) {
        MPinRegistrationSoapRequest mpinSoapRequest = new MPinRegistrationSoapRequest();
        mpinSoapRequest.setUserName(prodUsername);
        mpinSoapRequest.setPassword(prodPassword);
        mpinSoapRequest.setMpin(request.getMpin());
        mpinSoapRequest.setConfirmMpin(request.getConfirmMpin());
        mpinSoapRequest.setDateTime(request.getDateTime());
        mpinSoapRequest.setMobileNumber(request.getMobileNumber());
        mpinSoapRequest.setRrn(request.getRrn());
        mpinSoapRequest.setChannelId(CHANNEL_ID);
        mpinSoapRequest.setTerminalId(TERMINAL_ID);
        mpinSoapRequest.setReserved1(request.getReserved1());
        mpinSoapRequest.setReserved2(request.getReserved2());
        mpinSoapRequest.setReserved3(request.getReserved3());
        mpinSoapRequest.setReserved4(request.getReserved4());
        mpinSoapRequest.setReserved5(request.getReserved5());
        return mpinRegistrationResponse(mpinSoapRequest, prodUrl, "P");
    }

    // ---------- Core SOAP Logic ----------
    public MPinRegistrationResponse mpinRegistrationResponse(MPinRegistrationSoapRequest mpinRequest, String url, String env) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mpinRequest.getUserName())
                .append(mpinRequest.getPassword())
                .append(mpinRequest.getMpin())
                .append(mpinRequest.getConfirmMpin())
                .append(mpinRequest.getDateTime())
                .append(mpinRequest.getMobileNumber())
                .append(mpinRequest.getRrn())
                .append(mpinRequest.getChannelId())
                .append(mpinRequest.getTerminalId())
                .append(mpinRequest.getReserved1())
                .append(mpinRequest.getReserved2())
                .append(mpinRequest.getReserved3())
                .append(mpinRequest.getReserved4())
                .append(mpinRequest.getReserved5());

        String hashData = DigestUtils.sha256Hex(stringBuilder.toString());

        StringBuilder requestStringBuilder = new StringBuilder(
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <tem:mpinRegistrationRequest>\n" +
                        "         <UserName>" + mpinRequest.getUserName() + "</UserName>\n" +
                        "         <Password>" + mpinRequest.getPassword() + "</Password>\n" +
                        "         <Mpin>" + mpinRequest.getMpin() + "</Mpin>\n" +
                        "         <ConfirmMpin>" + mpinRequest.getConfirmMpin() + "</ConfirmMpin>\n" +
                        "         <DateTime>" + mpinRequest.getDateTime() + "</DateTime>\n" +
                        "         <MobileNumber>" + mpinRequest.getMobileNumber() + "</MobileNumber>\n" +
                        "         <Rrn>" + mpinRequest.getRrn() + "</Rrn>\n" +
                        "         <ChannelId>" + mpinRequest.getChannelId() + "</ChannelId>\n" +
                        "         <TerminalId>" + mpinRequest.getTerminalId() + "</TerminalId>\n" +
                        "         <Reserved1>" + mpinRequest.getReserved1() + "</Reserved1>\n" +
                        "         <Reserved2>" + mpinRequest.getReserved2() + "</Reserved2>\n" +
                        "         <Reserved3>" + mpinRequest.getReserved3() + "</Reserved3>\n" +
                        "         <Reserved4>" + mpinRequest.getReserved4() + "</Reserved4>\n" +
                        "         <Reserved5>" + mpinRequest.getReserved5() + "</Reserved5>\n" +
                        "         <HashData>" + hashData + "</HashData>\n" +
                        "      </tem:mpinRegistrationRequest>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>"
        );

        MPinRegistrationResponse mpinResponse = new MPinRegistrationResponse();

        try {
            JSONObject jObject = Util.getSoapResponseFromDebitWsdl(url, requestStringBuilder.toString());
            if (jObject != null) {
                jObject = jObject.getJSONObject("soap:Envelope")
                        .getJSONObject("soap:Body")
                        .getJSONObject("ns2:mpinRegistrationResponse");

                String responseCode = jObject.has("ResponseCode") ? jObject.get("ResponseCode").toString() : null;

                if (responseCode != null) {
                    if (responseCode.equals("00")) {
                        mpinResponse.setResponseCode(responseCode);
                        mpinResponse.setResponseDescription("Successful");
                    } else {
                        mpinResponse.setResponseCode(responseCode);
                        mpinResponse.setResponseDescription(
                                jObject.has("ResponseDescription")
                                        ? jObject.get("ResponseDescription").toString()
                                        : null
                        );
                    }
                } else {
                    mpinResponse.setResponseDescription("Service not available");
                }
            } else {
                mpinResponse.setResponseDescription("Service not available");
            }
        } catch (Exception e) {
            mpinResponse.setResponseDescription(e.getLocalizedMessage());
        }

        return mpinResponse;
    }
}
