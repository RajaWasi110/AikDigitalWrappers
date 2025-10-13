package com.aik.aikdigitalwrappers.service;

import com.aik.aikdigitalwrappers.config.ExternalApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class SoapClientService {

    private final ExternalApiProperties apiProps;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.profiles.active:dev}")
    private String profile;

    public String sendSoap(String soapAction, String xmlBody) {
        // 🧠 Dev mode — return mock JSON (not XML)
        if ("dev".equalsIgnoreCase(profile)) {
            return """
                {
                  "responseCode": "00",
                  "responseDescription": "Mock SOAP Success",
                  "rrn": "9999999999999999"
                }
            """;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);
        headers.add("SOAPAction", soapAction);
        HttpEntity<String> request = new HttpEntity<>(xmlBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                apiProps.getEndpoints().getSoapBase(),
                request,
                String.class
        );

        return response.getBody();
    }
}
