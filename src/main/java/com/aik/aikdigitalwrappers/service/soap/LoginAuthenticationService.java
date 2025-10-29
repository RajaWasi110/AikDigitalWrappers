package com.aik.aikdigitalwrappers.service.soap;

import com.aik.aikdigitalwrappers.dto.soap.requests.LoginAuthenticationRequest;
import com.aik.aikdigitalwrappers.dto.soap.requests.LoginAuthenticationSoapRequest;
import com.aik.aikdigitalwrappers.dto.soap.responses.LoginAuthenticationResponse;
import com.aik.aikdigitalwrappers.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginAuthenticationService {

    @Value("${loginauthentication.uat.url}")
    private String uatUrl;

    @Value("${loginauthentication.prod.url}")
    private String prodUrl;

    @Value("${uat.username}")
    private String uatUsername;

    @Value("${uat.password}")
    private String uatPassword;

    @Value("${prod.username}")
    private String prodUsername;

    @Value("${prod.password}")
    private String prodPassword;

    @Value("${loginauthentication.action}")
    private String soapAction;

    private static final String CHANNEL_ID = "NOVA";

    // ---------- Public Endpoints ----------
    public LoginAuthenticationResponse loginAuthenticationUat(LoginAuthenticationRequest request) {
        LoginAuthenticationSoapRequest loginSoapRequest = new LoginAuthenticationSoapRequest();
        loginSoapRequest.setUserName(uatUsername);
        loginSoapRequest.setPassword(uatPassword);
        loginSoapRequest.setMobileNumber(request.getMobileNumber());
        loginSoapRequest.setDateTime(request.getDateTime());
        loginSoapRequest.setRrn(request.getRrn());
        loginSoapRequest.setChannelId(CHANNEL_ID);
        loginSoapRequest.setPin(request.getPin());
        loginSoapRequest.setCnic(request.getCnic());
        return loginAuthenticationResponse(loginSoapRequest, uatUrl, "U");
    }

    public LoginAuthenticationResponse loginAuthenticationProd(LoginAuthenticationSoapRequest request) {
        LoginAuthenticationSoapRequest loginSoapRequest = new LoginAuthenticationSoapRequest();
        loginSoapRequest.setUserName(prodUsername);
        loginSoapRequest.setPassword(prodPassword);
        loginSoapRequest.setMobileNumber(request.getMobileNumber());
        loginSoapRequest.setDateTime(request.getDateTime());
        loginSoapRequest.setRrn(request.getRrn());
        loginSoapRequest.setChannelId(CHANNEL_ID);
        loginSoapRequest.setPin(request.getPin());
        loginSoapRequest.setCnic(request.getCnic());
        return loginAuthenticationResponse(loginSoapRequest, prodUrl, "P");
    }

    // ---------- Core SOAP Logic ----------
    public LoginAuthenticationResponse loginAuthenticationResponse(LoginAuthenticationSoapRequest loginRequest, String url, String env) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(loginRequest.getUserName())
                .append(loginRequest.getPassword())
                .append(loginRequest.getMobileNumber())
                .append(loginRequest.getDateTime())
                .append(loginRequest.getRrn())
                .append(loginRequest.getChannelId())
                .append(loginRequest.getPin())
                .append(loginRequest.getCnic() != null ? loginRequest.getCnic() : "");

        String hashData = DigestUtils.sha256Hex(stringBuilder.toString());

        StringBuilder requestStringBuilder = new StringBuilder(
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <tem:LoginAuthenticationRequest>\n" +
                        "         <UserName>" + loginRequest.getUserName() + "</UserName>\n" +
                        "         <Password>" + loginRequest.getPassword() + "</Password>\n" +
                        "         <MobileNumber>" + loginRequest.getMobileNumber() + "</MobileNumber>\n" +
                        "         <DateTime>" + loginRequest.getDateTime() + "</DateTime>\n" +
                        "         <Rrn>" + loginRequest.getRrn() + "</Rrn>\n" +
                        "         <ChannelId>" + loginRequest.getChannelId() + "</ChannelId>\n" +
                        "         <PIN>" + loginRequest.getPin() + "</PIN>\n" +
                        "         <Cnic>" + (loginRequest.getCnic() != null ? loginRequest.getCnic() : "") + "</Cnic>\n" +
                        "         <HashData>" + hashData + "</HashData>\n" +
                        "      </tem:LoginAuthenticationRequest>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>"
        );

        LoginAuthenticationResponse loginResponse = new LoginAuthenticationResponse();

        try {
            JSONObject jObject = Util.getSoapResponseFromDebitWsdl(url, requestStringBuilder.toString());
            if (jObject != null) {
                jObject = jObject.getJSONObject("soap:Envelope")
                        .getJSONObject("soap:Body")
                        .getJSONObject("ns2:loginAuthenticationResponse");

                String responseCode = jObject.has("ResponseCode") ? jObject.get("ResponseCode").toString() : null;

                if (responseCode != null) {
                    if (responseCode.equals("00")) {
                        loginResponse.setResponseCode(responseCode);
                        loginResponse.setResponseDescription("Successful");
                    } else {
                        loginResponse.setResponseCode(responseCode);
                        loginResponse.setResponseDescription(
                                jObject.has("ResponseDescription")
                                        ? jObject.get("ResponseDescription").toString()
                                        : null
                        );
                    }
                } else {
                    loginResponse.setResponseDescription("Service not available");
                }
            } else {
                loginResponse.setResponseDescription("Service not available");
            }
        } catch (Exception e) {
            loginResponse.setResponseDescription(e.getLocalizedMessage());
        }

        return loginResponse;
    }
}
