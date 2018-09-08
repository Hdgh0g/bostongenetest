package com.hdgh0g.bostongenetest.api.v1.responses;

import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.AppealStatus;
import com.hdgh0g.bostongenetest.domain.Translation;
import com.hdgh0g.bostongenetest.domain.TranslationStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.util.Optional;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListAdminAppealResponse {

    public static final int PREVIEW_TEXT_LIMIT = 50;

    private UUID id;
    private String shortenedText;
    private String username;
    private AppealStatus status;
    private TranslationStatus translationStatus;

    public static ListAdminAppealResponse fromAppeal(Appeal appeal) {
        ListAdminAppealResponse response = new ListAdminAppealResponse();
        response.id = appeal.getId();
        response.translationStatus = getTranslationStatus(appeal);
        response.shortenedText = getShortenedText(appeal, response.translationStatus);
        response.username = appeal.getUsername();
        response.status = appeal.getStatus();
        return response;
    }

    private static String getShortenedText(Appeal appeal, TranslationStatus translationStatus) {
        String text = translationStatus == TranslationStatus.TRANSLATED ? appeal.getTranslation().getTranslatedText() : appeal.getText();
        return StringUtils.substring(text, 0, PREVIEW_TEXT_LIMIT);
    }

    private static TranslationStatus getTranslationStatus(Appeal appeal) {
        return Optional.ofNullable(appeal.getTranslation())
                .map(Translation::getStatus)
                .orElse(TranslationStatus.TRANSLATION_NEEDED);
    }
}
