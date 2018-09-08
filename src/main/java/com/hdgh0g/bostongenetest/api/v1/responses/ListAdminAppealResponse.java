package com.hdgh0g.bostongenetest.api.v1.responses;

import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.Translation;
import com.hdgh0g.bostongenetest.domain.TranslationStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ListAdminAppealResponse extends BasicListAppealResponse {

    protected String username;
    protected TranslationStatus translationStatus;

    public static ListAdminAppealResponse fromAppeal(Appeal appeal) {
        ListAdminAppealResponse response = new ListAdminAppealResponse();
        fillListParams(appeal, response);
        return response;
    }

    protected static void fillListParams(Appeal appeal, ListAdminAppealResponse response) {
        response.id = appeal.getId();
        response.translationStatus = getTranslationStatus(appeal);
        response.text = response.translationStatus == TranslationStatus.TRANSLATED
                ? appeal.getTranslation().getTranslatedText() : appeal.getText();
        response.status = appeal.getStatus();
        response.username = appeal.getUsername();
    }

    private static TranslationStatus getTranslationStatus(Appeal appeal) {
        return Optional.ofNullable(appeal.getTranslation())
                .map(Translation::getStatus)
                .orElse(TranslationStatus.TRANSLATION_NEEDED);
    }
}
