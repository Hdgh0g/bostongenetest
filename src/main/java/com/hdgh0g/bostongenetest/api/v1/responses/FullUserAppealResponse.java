package com.hdgh0g.bostongenetest.api.v1.responses;

import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.AppealAnswer;
import com.hdgh0g.bostongenetest.domain.Translation;
import com.hdgh0g.bostongenetest.domain.TranslationStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullUserAppealResponse extends ListUserAppealResponse {

    private String answer;
    private TranslationStatus answerTranslationStatus;

    @Override
    public String getText() {
        return text;
    }

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
