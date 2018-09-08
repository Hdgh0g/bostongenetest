package com.hdgh0g.bostongenetest.api.v1.responses;

import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.Translation;
import com.hdgh0g.bostongenetest.domain.TranslationStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListAdminAppealResponse extends BasicListAppealResponse {

    private String username;
    private TranslationStatus translationStatus;

    public static ListAdminAppealResponse fromAppeal(Appeal appeal) {
        ListAdminAppealResponse response = new ListAdminAppealResponse();
        response.id = appeal.getId();
        response.translationStatus = getTranslationStatus(appeal);
        response.text = response.translationStatus == TranslationStatus.TRANSLATED
                ? appeal.getTranslation().getTranslatedText() : appeal.getText();
        response.username = appeal.getUsername();
        response.status = appeal.getStatus();
        return response;
    }

    private static TranslationStatus getTranslationStatus(Appeal appeal) {
        return Optional.ofNullable(appeal.getTranslation())
                .map(Translation::getStatus)
                .orElse(TranslationStatus.TRANSLATION_NEEDED);
    }
}
