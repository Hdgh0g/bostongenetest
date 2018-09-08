package com.hdgh0g.bostongenetest.api.v1.responses;

import com.hdgh0g.bostongenetest.domain.Appeal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListUserAppealResponse extends BasicListAppealResponse {

    public static ListUserAppealResponse fromAppeal(Appeal appeal) {
        ListUserAppealResponse response = new ListUserAppealResponse();
        response.id = appeal.getId();
        response.text = appeal.getText();
        response.status = appeal.getStatus();
        return response;
    }
}
