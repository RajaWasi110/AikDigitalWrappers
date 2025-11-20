package com.aik.aikdigitalwrappers.service.soap;

import com.aik.aikdigitalwrappers.dto.soap.requests.VerifyAccountRequest;
import com.aik.aikdigitalwrappers.dto.soap.requests.VerifyAccountSoapRequest;
import com.aik.aikdigitalwrappers.dto.soap.responses.VerifyAccountResponse;
import com.aik.aikdigitalwrappers.exception.ExternalServiceException;
import com.aik.aikdigitalwrappers.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
        VerifyAccountSoapRequest verifyAccountSoapRequest= new VerifyAccountSoapRequest();
        verifyAccountSoapRequest.setUserName(uatUsername);
        verifyAccountSoapRequest.setPassword(uatPassword);
        verifyAccountSoapRequest.setDateTime(request.getDateTime());
        verifyAccountSoapRequest.setMobileNumber(request.getMobileNumber());
        verifyAccountSoapRequest.setRrn(request.getRrn());
        verifyAccountSoapRequest.setTransactionType(request.getTransactionType());
        verifyAccountSoapRequest.setChannelId(request.getChannelId());
        verifyAccountSoapRequest.setReserved1(request.getReserved1());
        verifyAccountSoapRequest.setReserved2(request.getReserved2());
        verifyAccountSoapRequest.setReserved3(request.getReserved3());
        verifyAccountSoapRequest.setReserved4(request.getReserved4());
        verifyAccountSoapRequest.setReserved5(request.getReserved5());
        return verifyAccountResponse(verifyAccountSoapRequest,uatUrl,"U");
    }

    public VerifyAccountResponse verifyAccountProd(VerifyAccountSoapRequest request) {
        VerifyAccountSoapRequest verifyAccountSoapRequest= new VerifyAccountSoapRequest();
        verifyAccountSoapRequest.setUserName(prodUsername);
        verifyAccountSoapRequest.setPassword(prodPassword);
        verifyAccountSoapRequest.setDateTime(request.getDateTime());
        verifyAccountSoapRequest.setMobileNumber(request.getMobileNumber());
        verifyAccountSoapRequest.setRrn(request.getRrn());
        verifyAccountSoapRequest.setTransactionType(request.getTransactionType());
        verifyAccountSoapRequest.setChannelId(request.getChannelId());
        verifyAccountSoapRequest.setReserved1(request.getReserved1());
        verifyAccountSoapRequest.setReserved2(request.getReserved2());
        verifyAccountSoapRequest.setReserved3(request.getReserved3());
        verifyAccountSoapRequest.setReserved4(request.getReserved4());
        verifyAccountSoapRequest.setReserved5(request.getReserved5());
        return verifyAccountResponse(verifyAccountSoapRequest,prodUrl,"P");
    }

    // ---------- Core SOAP Logic ----------
    public VerifyAccountResponse verifyAccountResponse(VerifyAccountSoapRequest verifyAccountRequest, String url,String env) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(verifyAccountRequest.getUserName()).
                append(verifyAccountRequest.getPassword()).
                append(verifyAccountRequest.getCnic()).
                append(verifyAccountRequest.getDateTime()).
                append(verifyAccountRequest.getMobileNumber()).
                append(verifyAccountRequest.getRrn()).
                append(verifyAccountRequest.getTransactionType()).
                append(verifyAccountRequest.getChannelId()).
                append(verifyAccountRequest.getReserved1()).
                append(verifyAccountRequest.getReserved2()).
                append(verifyAccountRequest.getReserved3()).
                append(verifyAccountRequest.getReserved4()).
                append(verifyAccountRequest.getReserved5());


        String hashData = DigestUtils.sha256Hex(stringBuilder.toString());
        StringBuilder requestStringBuilder = new StringBuilder("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <tem:verifyAccountRequest>\n" +
                "         <!--Optional:-->\n" +
                "         <UserName>"+ verifyAccountRequest.getUserName() +"</UserName>\n" +
                "         <!--Optional:-->\n" +
                "         <Password>"+ verifyAccountRequest.getPassword() +"</Password>\n" +
                "         <!--Optional:-->\n" +
                "         <Cnic>"+ verifyAccountRequest.getCnic() +"</Cnic>\n" +
                "         <!--Optional:-->\n" +
                "         <DateTime>"+ verifyAccountRequest.getDateTime() +"</DateTime>\n" +
                "         <!--Optional:-->\n" +
                "         <MobileNumber>"+ verifyAccountRequest.getMobileNumber() +"</MobileNumber>\n" +
                "         <!--Optional:-->\n" +
                "         <Rrn>"+ verifyAccountRequest.getRrn() +"</Rrn>\n" +
                "         <!--Optional:-->\n" +
                "         <TransactionType>"+ verifyAccountRequest.getTransactionType() +"</TransactionType>\n" +
                "         <!--Optional:-->\n" +
                "         <ChannelId>"+ verifyAccountRequest.getChannelId() +"</ChannelId>\n" +
                "         <!--Optional:-->\n" +
                "         <Reserved1>"+ verifyAccountRequest.getReserved1() +"</Reserved1>\n" +
                "         <!--Optional:-->\n" +
                "         <Reserved2>"+ verifyAccountRequest.getReserved2() +"</Reserved2>\n" +
                "         <!--Optional:-->\n" +
                "         <Reserved3>"+ verifyAccountRequest.getReserved3() +"</Reserved3>\n" +
                "         <!--Optional:-->\n" +
                "         <Reserved4>"+ verifyAccountRequest.getReserved4() +"</Reserved4>\n" +
                "         <!--Optional:-->\n" +
                "         <Reserved5>"+ verifyAccountRequest.getReserved5() +"</Reserved5>\n" +
                "         <!--Optional:-->\n" +
                "         <HashData>"+ hashData +"</HashData>\n" +
                "      </tem:verifyAccountRequest>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>");

        VerifyAccountResponse verifyAccountResponse = new VerifyAccountResponse();

        try {
            JSONObject jObject = Util.getSoapResponseFromDebitWsdl(url, requestStringBuilder.toString());
            if (jObject != null) {
                jObject = jObject.getJSONObject("soap:Envelope");
                jObject = jObject.getJSONObject("soap:Body");
                jObject = jObject.getJSONObject("ns2:verifyAccountResponse");
                String responseCode = jObject.has("ResponseCode") ? (jObject.get("ResponseCode").toString()) : null;
                if (responseCode != null) {
                    if (responseCode.equals("00")) {

                        verifyAccountResponse.setResponseCode(responseCode);
                        verifyAccountResponse.setResponseDescription("Successful");

                    } else {
                        verifyAccountResponse.setResponseCode(responseCode);

                        verifyAccountResponse.setResponseDescription(jObject.has("ResponseDescription") ? (jObject.get("ResponseDescription")).toString() : null);
                    }
                } else verifyAccountResponse.setResponseDescription("Service not available");
            } else {
                verifyAccountResponse.setResponseDescription("Service not available");
            }
        } catch (Exception e) {
            verifyAccountResponse.setResponseDescription(e.getLocalizedMessage());
        }
        return verifyAccountResponse;
    }
}
