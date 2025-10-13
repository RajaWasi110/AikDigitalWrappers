package com.aik.aikdigitalwrappers.service;

import com.aik.aikdigitalwrappers.config.ExternalApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RestClientService {

    private final ExternalApiProperties apiProps;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.profiles.active:dev}")
    private String profile;

    public String postJson(String url, String jsonBody) {
        if ("dev".equalsIgnoreCase(profile)) {
            return """
                {
                  "responseCode": "00",
                  "responseDescription": "Mock Success",
                  "rrn": "9999999999999999"
                }
            """;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return response.getBody();
    }
}
