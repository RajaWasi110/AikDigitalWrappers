package com.aik.aikdigitalwrappers.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "external")
public class ExternalApiProperties {

    private Integer requestTimeoutMs;
    private Endpoints endpoints = new Endpoints();

    @Data
    public static class Endpoints {
        private String soapBase;
        private String blinkBase;
        private String verifyAccountSoapAction;
        private String loginPinSoapAction;
        private String resetPinSoapAction;
        private String mpinRegistrationSoapAction;
        private String loginAuthSoapAction;
    }
}
