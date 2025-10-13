package com.aik.aikdigitalwrappers.client;


import com.aik.aikdigitalwrappers.exception.ExternalApiException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.time.Duration;


@Component
@RequiredArgsConstructor
public class SoapClient {
    private static final Logger log = LoggerFactory.getLogger(SoapClient.class);


    private final WebClient webClient;
    private final XmlMapper xmlMapper = new XmlMapper();


    @Value("${external.request-timeout-ms:8000}")
    private long timeoutMs;


    @Value("${external.endpoints.soap-base}")
    private String soapBase;


    public String postSoap(String soapAction, String xmlBody) {
        String envelope = xmlBody; // caller provides full SOAP envelope
        log.debug("Posting SOAP to {} - action:{}", soapBase, soapAction);
        try {
            return webClient.post()
                    .uri(soapBase)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML_VALUE)
                    .header("SOAPAction", soapAction)
                    .bodyValue(envelope)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, resp -> resp.bodyToMono(String.class).flatMap(body -> Mono.error(new ExternalApiException("SOAP client error: " + body))))
                    .onStatus(HttpStatusCode::is5xxServerError, resp -> resp.bodyToMono(String.class).flatMap(body -> Mono.error(new ExternalApiException("SOAP server error: " + body))))
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(timeoutMs))
                    .block();
        } catch (Exception e) {
            throw new ExternalApiException("SOAP call failed: " + e.getMessage(), e);
        }
    }
}