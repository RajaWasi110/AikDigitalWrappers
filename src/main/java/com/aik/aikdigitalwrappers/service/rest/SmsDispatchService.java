package com.aik.aikdigitalwrappers.service.rest;

import com.aik.aikdigitalwrappers.dto.rest.requests.SmsDispatchRequest;
import com.aik.aikdigitalwrappers.exception.ExternalServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class SmsDispatchService {

    private final RestTemplate restTemplate;

    // Fixed constants
    private static final String FIXED_APP_ID = "AD";
    private static final String FIXED_ADC_TRANCODE = "515";
    private static final String FIXED_DELIMITER = "^";
    private static final String FIXED_NET_ID = "MB";
    private static final String FIXED_TRAN_MSG = "ZIslamicMiddleware";
    private static final String FIXED_MCC = "MMC";
    private static final String TOKEN_SECRET = "BIPL@786";

    // URLs
    @Value("${smsdispatch.uat.url}")
    private String uatUrl;

    @Value("${smsdispatch.prod.url}")
    private String prodUrl;

    public SmsDispatchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // UAT
    public void sendSmsUat(SmsDispatchRequest request) {
        sendSms(request, uatUrl, "UAT");
    }

    // PROD
    public void sendSmsProd(SmsDispatchRequest request) {
        sendSms(request, prodUrl, "PROD");
    }

    private void sendSms(SmsDispatchRequest request, String url, String envLabel) {
        setFixedFields(request);
        request.setStrToken(generateToken(request));

        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(url, request, Void.class);
            // Nothing to return, just succeed silently
        } catch (HttpStatusCodeException ex) {
            throw new ExternalServiceException(
                    envLabel + " SMS Dispatch API failed",
                    ex.getRawStatusCode(),
                    ex.getResponseBodyAsString()
            );
        } catch (Exception ex) {
            throw new ExternalServiceException(
                    "Unexpected error calling " + envLabel + " SMS Dispatch API",
                    500,
                    ex.getMessage()
            );
        }
    }

    private void setFixedFields(SmsDispatchRequest request) {
        request.setStrAppID(FIXED_APP_ID);
        request.setStrADC_TranCode(FIXED_ADC_TRANCODE);
        request.setStrDelimiter(FIXED_DELIMITER);
        request.setStrNetID(FIXED_NET_ID);
        request.setStrTranMsg(FIXED_TRAN_MSG);
        request.setStrMCC(FIXED_MCC);
    }

    private String generateToken(SmsDispatchRequest request) {
        String raw = request.getStrDate() + request.getStrTime() + TOKEN_SECRET + request.getIntSTAN();
        return sha256(raw);
    }

    private String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
