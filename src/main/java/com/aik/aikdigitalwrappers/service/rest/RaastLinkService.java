package com.aik.aikdigitalwrappers.service.rest;

import com.aik.aikdigitalwrappers.dto.rest.requests.RaastLinkRequest;
import com.aik.aikdigitalwrappers.dto.rest.responses.RaastLinkResponse;
import com.aik.aikdigitalwrappers.exception.ExternalServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class RaastLinkService {
    private final RestTemplate restTemplate;
    private static final String FIXED_strMerName = "Fixed";

    //UAT URL
    @Value("${raastLink.uat.url}")
    private String uatUrl;

    //PROD URL
    @Value("${raastLink.prod.url}")
    private String prodUrl;

    public RaastLinkService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //UAT Service
    public RaastLinkResponse createRaastLinkUat(RaastLinkRequest request) {
        request.setStrMerName(FIXED_strMerName);
        try {
            ResponseEntity<RaastLinkResponse> response = restTemplate.postForEntity(uatUrl, request, RaastLinkResponse.class);
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            throw new ExternalServiceException("UAT Raast Link API Failed", ex.getRawStatusCode(), ex.getResponseBodyAsString());
        } catch (Exception ex) {
            throw new ExternalServiceException("Unexpected error calling UAT Raast Link API", 500, ex.getMessage());

        }


    }

    //PROD Service
    public RaastLinkResponse createRaastLinkProd(RaastLinkRequest request) {
        request.setStrMerName(FIXED_strMerName);
        try {
            ResponseEntity<RaastLinkResponse> response = restTemplate.postForEntity(prodUrl, request, RaastLinkResponse.class);
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            throw new ExternalServiceException("PROD Raast Link API Failed", ex.getRawStatusCode(), ex.getResponseBodyAsString());
        } catch (Exception ex) {
            throw new ExternalServiceException("Unexpected error calling Prod Raast Link API", 500, ex.getMessage());

        }


    }
}
