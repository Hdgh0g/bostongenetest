package com.hdgh0g.bostongenetest.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "translations")
@Data
public class Translation {

    @Id
    private UUID id;
    private String sourceLanguage;
    private String targetLanguage;
    @Enumerated(EnumType.STRING)
    private TranslationStatus status;
    private String translatedText;
}
