package com.hdgh0g.bostongenetest.api.v1.responses;

import com.hdgh0g.bostongenetest.domain.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullUserAppealResponse {

    private UUID id;
    private String text;
    private AppealStatus status;
    private String answer;
    private TranslationStatus answerTranslationStatus;

    public static FullUserAppealResponse fromAppeal(Appeal appeal) {
        FullUserAppealResponse response = new FullUserAppealResponse();
        response.id = appeal.getId();
        response.text = appeal.getText();
        response.status = appeal.getStatus();
        response.answerTranslationStatus = getAnswerTranslationStatus(appeal);
        response.answer = getAnswer(appeal, response.answerTranslationStatus);
        return response;
    }

    private static TranslationStatus getAnswerTranslationStatus(Appeal appeal) {
        return Optional.ofNullable(appeal.getAnswer())
                .map(AppealAnswer::getTranslation)
                .map(Translation::getStatus)
                .orElse(null);
    }

    private static String getAnswer(Appeal appeal, TranslationStatus answerTranslationStatus) {
        return Optional.ofNullable(appeal.getAnswer())
                .filter(answer -> answerTranslationStatus == TranslationStatus.TRANSLATED)
                .map(AppealAnswer::getTranslation)
                .map(Translation::getTranslatedText)
                .orElse(null);
    }
}
