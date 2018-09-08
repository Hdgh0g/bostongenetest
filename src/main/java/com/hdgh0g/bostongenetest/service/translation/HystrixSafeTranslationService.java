package com.hdgh0g.bostongenetest.service.translation;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Primary
@RequiredArgsConstructor
@Service("safeTranslationService")
public class HystrixSafeTranslationService implements TranslationService {

    private static final String TIMEOUT = "5000";
    private static final String TIMEOUT_PROPERTY = "execution.isolation.thread.timeoutInMilliseconds";

    private final TranslationService translationService;

    @Override
    @HystrixCommand(
            fallbackMethod = "detectLanguageFallback",
            commandProperties = @HystrixProperty(name = TIMEOUT_PROPERTY, value = TIMEOUT)
    )
    public Optional<String> detectLanguage(String text) {
        return translationService.detectLanguage(text);
    }

    @Override
    @HystrixCommand(
            fallbackMethod = "translateTextFallback",
            commandProperties = @HystrixProperty(name = TIMEOUT_PROPERTY, value = TIMEOUT)
    )
    public Optional<String> translateText(String text, String sourceLanguage, String targetLanguage) {
        if (Objects.equals(sourceLanguage, targetLanguage)) {
            return Optional.of(text);
        }
        return translationService.translateText(text, sourceLanguage, targetLanguage);
    }

    private Optional<String> detectLanguageFallback(String text) {
        return Optional.empty();
    }

    private Optional<String> translateTextFallback(String text, String sourceLanguage, String targetLanguage) {
        return Optional.empty();
    }
}
