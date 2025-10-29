package com.aik.aikdigitalwrappers.service.soap;

import com.aik.aikdigitalwrappers.dto.soap.requests.MPinLoginRequest;
import com.aik.aikdigitalwrappers.dto.soap.requests.MPinLoginSoapRequest;
import com.aik.aikdigitalwrappers.dto.soap.responses.MPinLoginResponse;
import com.aik.aikdigitalwrappers.exception.ExternalServiceException;
import com.aik.aikdigitalwrappers.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MPinLoginService {

    @Value("${mpinlogin.uat.url}")
    private String uatUrl;

    @Value("${mpinlogin.prod.url}")
    private String prodUrl;

    @Value("${uat.username}")
    private String uatUsername;

    @Value("${uat.password}")
    private String uatPassword;

    @Value("${prod.username}")
    private String prodUsername;

    @Value("${prod.password}")
    private String prodPassword;

    @Value("${mpinlogin.action}")
    private String soapAction;

    private static final String CHANNEL_ID = "NOVA";
    private static final String TERMINAL_ID = "NOVA";

    // ----------- UAT -----------
    public MPinLoginResponse mPinLoginUat(MPinLoginRequest request) {
        MPinLoginSoapRequest soapRequest = new MPinLoginSoapRequest();
        soapRequest.setUserName(uatUsername);
        soapRequest.setPassword(uatPassword);
        soapRequest.setMobileNumber(request.getMobileNumber());
        soapRequest.setDateTime(request.getDateTime());
        soapRequest.setRrn(request.getRrn());
        soapRequest.setChannelId(CHANNEL_ID);
        soapRequest.setTerminalId(TERMINAL_ID);
        soapRequest.setPin(request.getPin());
        soapRequest.setReserved1(request.getReserved1());
        soapRequest.setReserved2(request.getReserved2());
        return mPinLoginResponse(soapRequest, uatUrl, "U");
    }

    // ----------- PROD -----------
    public MPinLoginResponse mPinLoginProd(MPinLoginSoapRequest request) {
        MPinLoginSoapRequest soapRequest = new MPinLoginSoapRequest();
        soapRequest.setUserName(prodUsername);
        soapRequest.setPassword(prodPassword);
        soapRequest.setMobileNumber(request.getMobileNumber());
        soapRequest.setDateTime(request.getDateTime());
        soapRequest.setRrn(request.getRrn());
        soapRequest.setChannelId(CHANNEL_ID);
        soapRequest.setTerminalId(TERMINAL_ID);
        soapRequest.setPin(request.getPin());
        soapRequest.setReserved1(request.getReserved1());
        soapRequest.setReserved2(request.getReserved2());
        return mPinLoginResponse(soapRequest, prodUrl, "P");
    }

    // ----------- Core Logic -----------
    public MPinLoginResponse mPinLoginResponse(MPinLoginSoapRequest request, String url, String env) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getUserName())
                .append(request.getPassword())
                .append(request.getMobileNumber())
                .append(request.getDateTime())
                .append(request.getRrn())
                .append(request.getChannelId())
                .append(request.getTerminalId())
                .append(request.getPin())
                .append(request.getReserved1())
                .append(request.getReserved2());

        String hashData = DigestUtils.sha256Hex(sb.toString());
        request.setHashData(hashData);

        String soapRequest =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <tem:LoginPinRequest>\n" +
                        "         <UserName>" + request.getUserName() + "</UserName>\n" +
                        "         <Password>" + request.getPassword() + "</Password>\n" +
                        "         <MobileNumber>" + request.getMobileNumber() + "</MobileNumber>\n" +
                        "         <DateTime>" + request.getDateTime() + "</DateTime>\n" +
                        "         <Rrn>" + request.getRrn() + "</Rrn>\n" +
                        "         <ChannelId>" + request.getChannelId() + "</ChannelId>\n" +
                        "         <TerminalId>" + request.getTerminalId() + "</TerminalId>\n" +
                        "         <PIN>" + request.getPin() + "</PIN>\n" +
                        "         <Reserved1>" + request.getReserved1() + "</Reserved1>\n" +
                        "         <Reserved2>" + request.getReserved2() + "</Reserved2>\n" +
                        "         <HashData>" + hashData + "</HashData>\n" +
                        "      </tem:LoginPinRequest>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>";

        MPinLoginResponse response = new MPinLoginResponse();

        try {
            JSONObject json = Util.getSoapResponseFromDebitWsdl(url, soapRequest);
            if (json != null) {
                json = json.getJSONObject("soap:Envelope")
                        .getJSONObject("soap:Body")
                        .getJSONObject("ns2:loginPinResponse");
                String code = json.optString("ResponseCode", null);
                if (code != null) {
                    response.setResponseCode(code);
                    response.setResponseDescription(
                            code.equals("00") ? "Successful"
                                    : json.optString("ResponseDescription", "Unknown error"));
                } else {
                    response.setResponseDescription("Service not available");
                }
            } else {
                response.setResponseDescription("Service not available");
            }
        } catch (Exception e) {
            response.setResponseDescription(e.getLocalizedMessage());
        }

        return response;
    }
}
