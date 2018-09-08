package com.hdgh0g.bostongenetest.api.v1.responses;

import com.hdgh0g.bostongenetest.domain.AppealStatus;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ListAppealResponse {

    private UUID id;
    private String shortenedText;
    private AppealStatus status;
}
