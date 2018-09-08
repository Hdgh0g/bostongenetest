package com.hdgh0g.bostongenetest.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "translations")
@Data
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String sourceLanguage;
    private String targetLanguage;
    @Enumerated(EnumType.STRING)
    private TranslationStatus status;
    private String translatedText;

    public Translation(String source, String target, String text) {
        this.sourceLanguage = source;
        this.targetLanguage = target;
        this.translatedText = text;
        this.status = TranslationStatus.TRANSLATED;
    }

    public Translation(String source, String target) {
        this.sourceLanguage = source;
        this.targetLanguage = target;
        this.status = source == null ? TranslationStatus.NOT_DEFINED_ORIGINAL_LANGUAGE : TranslationStatus.ERROR_IN_TRANSLATION;
    }
}
