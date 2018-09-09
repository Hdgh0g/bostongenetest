package com.hdgh0g.bostongenetest.api.v1.responses;

import com.hdgh0g.bostongenetest.domain.AppealStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BasicListAppealResponse {

    public static final int PREVIEW_TEXT_LIMIT = 50;

    protected UUID id;
    protected String text;
    protected AppealStatus status;

    public String getText() {
        return StringUtils.substring(text, 0, PREVIEW_TEXT_LIMIT);
    }
}