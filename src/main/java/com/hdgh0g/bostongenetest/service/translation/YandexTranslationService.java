package com.hdgh0g.bostongenetest.service.translation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class YandexTranslationService implements TranslationService {

    @Value("${translation.yandex.detect}")
    private String detectUrl;

    @Value("${translation.yandex.translate}")
    private String translateUrl;

    @Override
    public Optional<String> detectLanguage(String text) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> request = getDetectRequest(text);
        YandexDetectResponse yandexDetectResponse = restTemplate.postForObject(detectUrl, request, YandexDetectResponse.class);
        return Optional.ofNullable(yandexDetectResponse)
                .filter(response -> response.getCode() == 200)
                .map(YandexDetectResponse::getLang);
    }

    @Override
    public Optional<String> translateText(String text, String sourceLanguage, String targetLanguage) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> request = getTranslateRequest(text, sourceLanguage, targetLanguage);
        YandexTranslateResponse yandexTranslateResponse = restTemplate.postForObject(translateUrl, request, YandexTranslateResponse.class);
        return Optional.ofNullable(yandexTranslateResponse)
                .filter(response -> response.getCode() == 200)
                .map(YandexTranslateResponse::getText)
                .filter(list -> list.size() == 1)
                .map(list -> list.get(0));
    }

    private HttpEntity<MultiValueMap<String, String>> getTranslateRequest(String text, String source, String target) {
        MultiValueMap<String, String> req = new LinkedMultiValueMap<>();
        req.add("text", text);
        req.add("lang", source + "-" + target);
        return new HttpEntity<>(req, getHeaders());
    }

    private HttpEntity<MultiValueMap<String, String>> getDetectRequest(String text) {
        MultiValueMap<String, String> req = new LinkedMultiValueMap<>();
        req.add("text", text);
        return new HttpEntity<>(req, getHeaders());
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    @Getter
    @Setter
    private static class YandexDetectResponse {
        private int code;
        private String lang;
    }

    @Getter
    @Setter
    private static class YandexTranslateResponse {
        private int code;
        private List<String> text;
    }
}
