package com.aik.aikdigitalwrappers.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
public class RestTemplateConfig {

    @Value("${blink.external.timeout-ms:5000}")
    private int timeoutMs;

    // Optional proxy configuration
    @Value("${blink.proxy.enabled:false}")
    private boolean proxyEnabled;

    @Value("${blink.proxy.host:}")
    private String proxyHost;

    @Value("${blink.proxy.port:0}")
    private int proxyPort;

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(timeoutMs);
        factory.setReadTimeout(timeoutMs);

        // ✅ Configure proxy if enabled
        if (proxyEnabled && proxyHost != null && !proxyHost.isEmpty() && proxyPort > 0) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            factory.setProxy(proxy);
            System.out.println("🔹 Proxy enabled for RestTemplate: " + proxyHost + ":" + proxyPort);
        } else {
            System.out.println("⚙️ Proxy disabled — connecting directly");
        }

        return new RestTemplate(factory);
    }
}
