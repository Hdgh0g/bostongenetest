package com.hdgh0g.bostongenetest.api.v1.responses;

import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.AppealStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListUserAppealResponse {

    public static final int PREVIEW_TEXT_LIMIT = 50;

    private UUID id;
    private String shortenedText;
    private AppealStatus status;

    public static ListUserAppealResponse fromAppeal(Appeal appeal) {
        ListUserAppealResponse response = new ListUserAppealResponse();
        response.id = appeal.getId();
        response.shortenedText = StringUtils.substring(appeal.getText(), 0, PREVIEW_TEXT_LIMIT);
        response.status = appeal.getStatus();
        return response;
    }
}
