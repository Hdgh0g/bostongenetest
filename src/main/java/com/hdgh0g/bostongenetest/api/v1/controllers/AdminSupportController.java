package com.hdgh0g.bostongenetest.api.v1.controllers;

import com.hdgh0g.bostongenetest.api.v1.requests.AnswerRequest;
import com.hdgh0g.bostongenetest.api.v1.responses.FullAppealResponse;
import com.hdgh0g.bostongenetest.api.v1.responses.ListAppealResponse;
import com.hdgh0g.bostongenetest.domain.AppealStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/admin/appeals")
public class AdminSupportController {

    @GetMapping
    public List<ListAppealResponse> getAppealList(@RequestParam(defaultValue = "OPEN") AppealStatus status,
                                                  Pageable pageable) {
        return Collections.emptyList();
    }

    @GetMapping("/{uuid}")
    public FullAppealResponse getAppealInfo(@PathVariable UUID uuid) {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void postAnswerAndClose(@RequestBody AnswerRequest answer) {

    }
}
