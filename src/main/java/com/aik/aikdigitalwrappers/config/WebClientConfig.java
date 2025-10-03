package com.aik.aikdigitalwrappers.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;


import java.time.Duration;


@Configuration
public class WebClientConfig {


    @Value("${external.default-base-url}")
    private String defaultBaseUrl;


    @Value("${external.request-timeout-ms:5000}")
    private long requestTimeoutMs;


    @Bean
    public WebClient defaultWebClient() {
        ConnectionProvider provider = ConnectionProvider.builder("custom").maxConnections(100).build();
        HttpClient httpClient = HttpClient.create(provider)
                .responseTimeout(Duration.ofMillis(requestTimeoutMs));


        return WebClient.builder()
                .baseUrl(defaultBaseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                        .build())
                .build();
    }
}