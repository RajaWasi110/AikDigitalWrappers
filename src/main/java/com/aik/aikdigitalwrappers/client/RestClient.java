package com.aik.aikdigitalwrappers.client;


import com.aik.aikdigitalwrappers.exception.ExternalApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.time.Duration;


@Component
@RequiredArgsConstructor
public class RestClient {
    private static final Logger log = LoggerFactory.getLogger(RestClient.class);


    private final WebClient webClient;


    @Value("${external.request-timeout-ms:8000}")
    private long timeoutMs;


    public <T> T postJson(String url, Object request, Class<T> responseType) {
        try {
            return webClient.post()
                    .uri(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, resp -> resp.bodyToMono(String.class).flatMap(body -> Mono.error(new ExternalApiException("REST client error: " + body))))
                    .onStatus(HttpStatusCode::is5xxServerError, resp -> resp.bodyToMono(String.class).flatMap(body -> Mono.error(new ExternalApiException("REST server error: " + body))))
                    .bodyToMono(responseType)
                    .timeout(Duration.ofMillis(timeoutMs))
                    .block();
        } catch (Exception e) {
            throw new ExternalApiException("REST call failed: " + e.getMessage(), e);
        }
    }
}