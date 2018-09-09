package com.hdgh0g.bostongenetest.service.translation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class YandexTranslationService implements TranslationService {

    @Value("${translation.yandex.detect}")
    private String detectUrl;

    @Value("${translation.yandex.translate}")
    private String translateUrl;

    private final RestTemplate restTemplate;

    @Override
    public Optional<String> detectLanguage(String text) {
        MultiValueMap<String, String> request = getDetectRequest(text);
        DetectResponse detectResponse = restTemplate.postForObject(detectUrl, request, DetectResponse.class);
        return Optional.ofNullable(detectResponse)
                .filter(response -> response.getCode() == HttpStatus.OK.value())
                .map(DetectResponse::getLang);
    }

    @Override
    public Optional<String> translateText(String text, String sourceLanguage, String targetLanguage) {
        MultiValueMap<String, String> request = getTranslateRequest(text, sourceLanguage, targetLanguage);
        TranslateResponse translateResponse = restTemplate.postForObject(translateUrl, request, TranslateResponse.class);
        return Optional.ofNullable(translateResponse)
                .filter(response -> response.getCode() == HttpStatus.OK.value())
                .map(TranslateResponse::getText)
                .filter(list -> list.size() == 1)
                .map(list -> list.get(0));
    }

    private MultiValueMap<String, String> getTranslateRequest(String text, String source, String target) {
        MultiValueMap<String, String> req = new LinkedMultiValueMap<>();
        req.add("text", text);
        req.add("lang", source + "-" + target);
        return req;
    }

    private MultiValueMap<String, String> getDetectRequest(String text) {
        MultiValueMap<String, String> req = new LinkedMultiValueMap<>();
        req.add("text", text);
        return req;
    }

    @Getter
    @Setter
    static class DetectResponse {
        private int code;
        private String lang;
    }

    @Getter
    @Setter
    static class TranslateResponse {
        private int code;
        private List<String> text;
    }
}
