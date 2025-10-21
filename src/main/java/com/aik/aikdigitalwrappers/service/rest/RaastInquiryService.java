package com.aik.aikdigitalwrappers.service.rest;

import com.aik.aikdigitalwrappers.dto.rest.requests.RaastInquiryRequest;
import com.aik.aikdigitalwrappers.dto.rest.responses.RaastInquiryResponse;
import com.aik.aikdigitalwrappers.exception.ExternalServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class RaastInquiryService {
    private final RestTemplate restTemplate;

    //UAT Url
    @Value("{raastinquiry.uat.url}")
    private String uatUrl;
    //PROD Url
    @Value("{raastinquiry.prod.url}")
    private String prodUrl;

    public RaastInquiryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //UAT Service
    public RaastInquiryResponse createRaastInquiryUat(RaastInquiryRequest request) {

        try {
            ResponseEntity<RaastInquiryResponse> response = restTemplate.postForEntity(uatUrl, request, RaastInquiryResponse.class);
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            throw new ExternalServiceException("UAT Raast Inquiry API Failed", ex.getRawStatusCode(), ex.getResponseBodyAsString());
        } catch (Exception ex) {
            throw new ExternalServiceException("Unexpected error calling UAT Raast Inquiry API", 500, ex.getMessage());

        }
    }

    //PROD Service
    public RaastInquiryResponse createRaastInquiryProd(RaastInquiryRequest request) {

        try {
            ResponseEntity<RaastInquiryResponse> response = restTemplate.postForEntity(prodUrl, request, RaastInquiryResponse.class);
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            throw new ExternalServiceException("PROD Raast Inquiry API Failed", ex.getRawStatusCode(), ex.getResponseBodyAsString());
        } catch (Exception ex) {
            throw new ExternalServiceException("Unexpected error calling PROD Raast Inquiry API", 500, ex.getMessage());

        }
    }
}
