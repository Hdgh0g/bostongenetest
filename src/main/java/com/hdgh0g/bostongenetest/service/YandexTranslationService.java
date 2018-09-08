package com.hdgh0g.bostongenetest.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class YandexTranslationService implements TranslationService {

    @Override
    public Optional<String> detectLanguage(String text) {
        return Optional.empty();
    }

    @Override
    public Optional<String> translateText(String text, String sourceLanguage, String targetLanguage) {
        return Optional.empty();
    }
}
