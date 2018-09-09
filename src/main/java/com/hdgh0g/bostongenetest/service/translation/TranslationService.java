package com.hdgh0g.bostongenetest.service.translation;

import java.util.Optional;

public interface TranslationService {

    Optional<String> detectLanguage(String text);

    Optional<String> translateText(String text, String sourceLanguage, String targetLanguage);
}
