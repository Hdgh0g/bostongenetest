package com.hdgh0g.bostongenetest.api.v1.controllers;

import com.hdgh0g.bostongenetest.api.v1.requests.AppealRequest;
import com.hdgh0g.bostongenetest.api.v1.responses.FullAppealResponse;
import com.hdgh0g.bostongenetest.api.v1.responses.ListAppealResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/appeals")
public class UserAppealController {

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendAppeal(@RequestBody AppealRequest appeal) {

    }

    @GetMapping
    public List<ListAppealResponse> getCurrentUserAppeals(Pageable pageable) {
        return Collections.emptyList();
    }

    @GetMapping
    public FullAppealResponse getAppealForUser(UUID uuid) {
        return null;
    }
}
