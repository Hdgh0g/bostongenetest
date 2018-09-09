package com.hdgh0g.bostongenetest.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate defaultRestTemplate(RestTemplateBuilder builder,
                                            ClientHttpRequestInterceptor contentTypeHeaderInterceptor) {
        return builder.interceptors(contentTypeHeaderInterceptor).build();
    }

    @Bean
    public ClientHttpRequestInterceptor contentTypeHeaderInterceptor() {
        return (request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            return execution.execute(request, body);
        };
    }
}
