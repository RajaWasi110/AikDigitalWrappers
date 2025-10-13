package com.aik.aikdigitalwrappers.client;

import com.aik.aikdigitalwrappers.exception.ExternalApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class ExternalApiClient {

    private static final Logger log = LoggerFactory.getLogger(ExternalApiClient.class);

    private final WebClient defaultWebClient;

    @Value("${external.request-timeout-ms:5000}")
    private long requestTimeoutMs;

    private <T> T doPost(String fullUrl, Object requestBody, Class<T> responseType) {
        try {
            log.debug("Calling external API: {} with body: {}", fullUrl, requestBody);
            return defaultWebClient.post()
                    .uri(fullUrl)
                    .bodyValue(requestBody)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, this::handle4xx)   // ✅ fixed
                    .onStatus(HttpStatusCode::is5xxServerError, this::handle5xx)   // ✅ fixed
                    .bodyToMono(responseType)
                    .timeout(Duration.ofMillis(requestTimeoutMs))
                    .doOnError(throwable -> log.error("External call failed: {}", throwable.getMessage()))
                    .block();
        } catch (ExternalApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ExternalApiException("Failed to call external API: " + e.getMessage(), e);
        }
    }

    private Mono<? extends Throwable> handle4xx(ClientResponse cr) {
        return cr.bodyToMono(String.class)
                .flatMap(body -> Mono.error(
                        new ExternalApiException("Client error: " + cr.statusCode().value() + " - " + body)
                ));
    }

    private Mono<? extends Throwable> handle5xx(ClientResponse cr) {
        return cr.bodyToMono(String.class)
                .flatMap(body -> Mono.error(
                        new ExternalApiException("Server error: " + cr.statusCode().value() + " - " + body)
                ));
    }

    // Public wrapper method
    public <T> T post(String endpointOrPath, Object request, Class<T> responseType) {
        String target = endpointOrPath;
        return doPost(target, request, responseType);
    }
}
