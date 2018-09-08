package com.hdgh0g.bostongenetest.api.v1.responses;

import com.hdgh0g.bostongenetest.domain.AppealStatus;
import com.hdgh0g.bostongenetest.domain.TranslationStatus;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FullAppealResponse {

    private UUID id;
    private String text;
    private AppealStatus status;
    private String answer;
    private TranslationStatus answerTranslationStatus;

}
