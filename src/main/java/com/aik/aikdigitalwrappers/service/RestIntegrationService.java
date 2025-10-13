package com.aik.aikdigitalwrappers.service;


import com.aik.aikdigitalwrappers.client.RestClient;
import com.aik.aikdigitalwrappers.dto.requests.BlinkAccountRequest;
import com.aik.aikdigitalwrappers.dto.responses.BlinkAccountResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class RestIntegrationService {


    private final RestClient restClient;


    @Value("${external.endpoints.blink-base}")
    private String blinkBase;


    public RestIntegrationService(RestClient restClient) {
        this.restClient = restClient;
    }


    public BlinkAccountResponse blinkAccount(BlinkAccountRequest req) {
        String url = blinkBase + "/blinkaccount";
// inject hardcoded credentials
        var payload = new java.util.HashMap<String, Object>();
        payload.put("userName", "ApiGee@JS");
        payload.put("password", "ApiGee@JS");
        payload.put("cnic", req.getCnic());
        payload.put("dateTime", req.getDateTime());
        payload.put("mobileNumber", req.getMobileNumber());
        payload.put("rrn", req.getRrn());
        payload.put("consumerName", req.getConsumerName());
        payload.put("accountTitle", req.getAccountTitle());
// ... add other fields as required


        return restClient.postJson(url, payload, BlinkAccountResponse.class);
    }
}