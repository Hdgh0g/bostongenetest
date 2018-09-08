package com.hdgh0g.bostongenetest.api.v1.responses;

import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.AppealStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListAppealResponse {

    private UUID id;
    private String shortenedText;
    private AppealStatus status;

    public static ListAppealResponse forUser(Appeal appeal) {
        ListAppealResponse response = new ListAppealResponse();
        response.id = appeal.getId();
        response.shortenedText = appeal.getText();
        response.status = appeal.getStatus();
        return response;
    }
}
