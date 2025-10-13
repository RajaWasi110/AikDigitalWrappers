package com.aik.aikdigitalwrappers.service;

import com.aik.aikdigitalwrappers.client.SoapClient;
import com.aik.aikdigitalwrappers.dto.requests.VerifyAccountRequest;
import com.aik.aikdigitalwrappers.dto.responses.VerifyAccountResponse;
import com.aik.aikdigitalwrappers.util.HashUtil;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SoapIntegrationService {


    private final SoapClient soapClient;
    private final XmlMapper xmlMapper = new XmlMapper();


    @Value("${external.endpoints.verify-account-soap-action}")
    private String verifyAction;


    public SoapIntegrationService(SoapClient soapClient) {
        this.soapClient = soapClient;
    }


    public VerifyAccountResponse verifyAccount(VerifyAccountRequest req) {
// Build HashData input string — define scheme: UserName+Password+CNIC+DateTime+Mobile+Rrn
        String user = "ApiGee@JS";
        String pass = "ApiGee@JS";
        String hashInput = user + pass + req.getCnic() + req.getDateTime() + req.getMobileNumber() + req.getRrn();
        String hash = HashUtil.sha256Hex(hashInput);


// Build SOAP XML
        String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<tem:verifyAccountRequest>" +
                "<UserName>" + user + "</UserName>" +
                "<Password>" + pass + "</Password>" +
                "<Cnic>" + req.getCnic() + "</Cnic>" +
                "<DateTime>" + req.getDateTime() + "</DateTime>" +
                "<MobileNumber>" + req.getMobileNumber() + "</MobileNumber>" +
                "<Rrn>" + req.getRrn() + "</Rrn>" +
                "<TransactionType>01</TransactionType>" +
                "<ChannelId>APIGEE</ChannelId>" +
                "<Reserved1>01</Reserved1>" +
                "<Reserved2></Reserved2>" +
                "<Reserved3></Reserved3>" +
                "<Reserved4></Reserved4>" +
                "<Reserved5></Reserved5>" +
                "<HashData>" + hash + "</HashData>" +
                "</tem:verifyAccountRequest>" +
                "</soapenv:Body></soapenv:Envelope>";


        String responseXml = soapClient.postSoap(verifyAction, xml);


// parse response (simple parsing using XmlMapper)
        try {
// Map the inner response node to a POJO — we'll extract by simple substring search
// Find <ns2:verifyAccountResponse ...> or <verifyAccountResponse>
            int start = responseXml.indexOf("<verifyAccountResponse");
            if (start == -1) start = responseXml.indexOf("<ns2:verifyAccountResponse");
            int end = responseXml.indexOf("</verifyAccountResponse>");
            if (start != -1 && end != -1) {
                String inner = responseXml.substring(start, end + "</verifyAccountResponse>".length());
// Replace namespaces to make mapping simple
                inner = inner.replaceAll("ns2:", "");
                VerifyAccountResponse resp = xmlMapper.readValue(inner, VerifyAccountResponse.class);
// normalize field names if needed
                return resp;
            } else {
                throw new RuntimeException("Unable to parse SOAP response: " + responseXml);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse verifyAccount response", e);
        }
    }
}