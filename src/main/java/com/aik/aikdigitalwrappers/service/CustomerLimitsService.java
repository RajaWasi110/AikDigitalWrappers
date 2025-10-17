package com.aik.aikdigitalwrappers.service;

import com.aik.aikdigitalwrappers.dto.requests.CustomerLimitsRequest;
import com.aik.aikdigitalwrappers.dto.responses.CustomerLimitsResponse;
import com.aik.aikdigitalwrappers.exception.ExternalServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public class CustomerLimitsService {
    private final RestTemplate restTemplate;

    //UAT Url
    @Value("{customerLimits.uat.url}")
    private String uatUrl;

    //Prod Url
    @Value("{customerLimits.prod.url}")
    private String prodUrl;

    public CustomerLimitsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //UAT Service
    public CustomerLimitsResponse createCustomerLimitsUat(CustomerLimitsRequest request) {
        try {
            ResponseEntity<CustomerLimitsResponse> response = restTemplate.postForEntity(prodUrl, request, CustomerLimitsResponse.class);
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            throw new ExternalServiceException("UAT Raast Inquiry API Failed", ex.getRawStatusCode(), ex.getResponseBodyAsString());
        } catch (Exception ex) {
            throw new ExternalServiceException("Unexpected error calling UAT Raast Inquiry API", 500, ex.getMessage());

        }
    }

    //PROD Service
    public CustomerLimitsResponse createCustomerLimitsProd(CustomerLimitsRequest request) {
        try {
            ResponseEntity<CustomerLimitsResponse> response = restTemplate.postForEntity(uatUrl, request, CustomerLimitsResponse.class);
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            throw new ExternalServiceException("UAT Raast Inquiry API Failed", ex.getRawStatusCode(), ex.getResponseBodyAsString());
        } catch (Exception ex) {
            throw new ExternalServiceException("Unexpected error calling UAT Raast Inquiry API", 500, ex.getMessage());

        }
    }
}
