package com.hdgh0g.bostongenetest.tasks;

import com.hdgh0g.bostongenetest.domain.Translation;
import com.hdgh0g.bostongenetest.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractTranslationTask<T> {

    private static final int DELAY = 60_000;
    private static final int INITIAL_DELAY = 5_000;
    static String DEFAULT_LANGUAGE = "English";

    protected final TranslationService translationService;

    @Scheduled(initialDelay = INITIAL_DELAY, fixedDelay = DELAY)
    public void runTask() {
        List<T> entitiesToTranslate = getEntitiesToTranslate();
        entitiesToTranslate.forEach(this::translateAndSave);
    }

    private void translateAndSave(T entity) {
        String textToTranslate = getTextToTranslate(entity);
        Optional<String> targetLanguage = detectTargetLanguage(entity);
        Optional<String> sourceLanguage = detectSourceLanguage(textToTranslate);
        Optional<String> translatedText = sourceLanguage
                .filter(sourceLang -> targetLanguage.isPresent())
                .flatMap(sourceLang -> translationService.translateText(textToTranslate, sourceLang, targetLanguage.get()));
        if (translatedText.isPresent()) {
            saveTranslation(entity, new Translation(sourceLanguage.get(), targetLanguage.get(), translatedText.get()));
        } else {
            saveTranslation(entity, new Translation(sourceLanguage.orElse(null), targetLanguage.orElse(null)));
        }
    }

    protected abstract List<T> getEntitiesToTranslate();

    protected abstract String getTextToTranslate(T entity);

    protected abstract Optional<String> detectSourceLanguage(String sourceText);

    protected abstract Optional<String> detectTargetLanguage(T entity);

    protected abstract void saveTranslation(T entity, Translation translation);


}
