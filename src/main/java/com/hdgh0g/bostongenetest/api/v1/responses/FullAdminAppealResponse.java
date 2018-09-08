package com.hdgh0g.bostongenetest.api.v1.responses;

import com.hdgh0g.bostongenetest.domain.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullAdminAppealResponse {

    private UUID id;
    private String text;
    private String username;
    private TranslationStatus translationStatus;
    private AppealStatus status;
    private String answer;

    public static FullAdminAppealResponse fromAppeal(Appeal appeal) {
        FullAdminAppealResponse response = new FullAdminAppealResponse();
        response.id = appeal.getId();
        response.translationStatus = getTranslationStatus(appeal);
        response.text = response.translationStatus == TranslationStatus.TRANSLATED
                ? appeal.getTranslation().getTranslatedText() : appeal.getText();
        response.status = appeal.getStatus();
        response.username = appeal.getUsername();
        response.answer = Optional.ofNullable(appeal.getAnswer()).map(AppealAnswer::getText).orElse(null);
        return response;
    }

    private static TranslationStatus getTranslationStatus(Appeal appeal) {
        return Optional.ofNullable(appeal.getTranslation())
                .map(Translation::getStatus)
                .orElse(TranslationStatus.TRANSLATION_NEEDED);
    }
}
