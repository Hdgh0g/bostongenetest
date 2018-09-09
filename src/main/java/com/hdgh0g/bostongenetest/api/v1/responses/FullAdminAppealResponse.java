package com.hdgh0g.bostongenetest.api.v1.responses;

import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.AppealAnswer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullAdminAppealResponse extends ListAdminAppealResponse {

    private String answer;

    @Override
    public String getText() {
        return text;
    }

    public static FullAdminAppealResponse fromAppeal(Appeal appeal) {
        FullAdminAppealResponse response = new FullAdminAppealResponse();
        fillListParams(appeal, response);
        response.answer = Optional.ofNullable(appeal.getAnswer()).map(AppealAnswer::getText).orElse(null);
        return response;
    }
}
