package com.aik.aikdigitalwrappers.service;


import com.aik.aikdigitalwrappers.dto.requests.BlinkAccountRequest;
import com.aik.aikdigitalwrappers.dto.responses.BlinkAccountResponse;
import com.aik.aikdigitalwrappers.exception.ExternalServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class BlinkAccountService {

    private final RestTemplate restTemplate;

    // UAT Credentials & URL
    @Value("${uat.username}")
    private String uatUsername;
    @Value("${uat.password}")
    private String uatPassword;
    @Value("${blinkaccount.uat.url}")
    private String uatUrl;

    // PROD Credentials & URL
    @Value("${prod.username}")
    private String prodUsername;
    @Value("${prod.password}")
    private String prodPassword;
    @Value("${blinkaccount.prod.url}")
    private String prodUrl;

    public BlinkAccountService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // =======================
    // UAT
    // =======================
    public BlinkAccountResponse createBlinkAccountUat(BlinkAccountRequest request) {
        request.setUserName(uatUsername);
        request.setPassword(uatPassword);
        try {
            ResponseEntity<BlinkAccountResponse> response =
                    restTemplate.postForEntity(uatUrl, request, BlinkAccountResponse.class);
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            throw new ExternalServiceException("UAT BlinkAccount API failed", ex.getRawStatusCode(), ex.getResponseBodyAsString());
        } catch (Exception ex) {
            throw new ExternalServiceException("Unexpected error calling UAT BlinkAccount API", 500, ex.getMessage());
        }
    }

    // =======================
    // PROD
    // =======================
    public BlinkAccountResponse createBlinkAccountProd(BlinkAccountRequest request) {
        request.setUserName(prodUsername);
        request.setPassword(prodPassword);
        try {
            ResponseEntity<BlinkAccountResponse> response =
                    restTemplate.postForEntity(prodUrl, request, BlinkAccountResponse.class);
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            throw new ExternalServiceException("PROD BlinkAccount API failed", ex.getRawStatusCode(), ex.getResponseBodyAsString());
        } catch (Exception ex) {
            throw new ExternalServiceException("Unexpected error calling PROD BlinkAccount API", 500, ex.getMessage());
        }
    }
}
