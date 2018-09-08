package com.hdgh0g.bostongenetest.api.v1.controllers;

import com.hdgh0g.bostongenetest.api.v1.requests.AppealRequest;
import com.hdgh0g.bostongenetest.api.v1.responses.FullUserAppealResponse;
import com.hdgh0g.bostongenetest.api.v1.responses.ListUserAppealResponse;
import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.exceptions.ApiException;
import com.hdgh0g.bostongenetest.exceptions.ApiExceptionCode;
import com.hdgh0g.bostongenetest.service.AppealService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1/appeals", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class UserAppealController {

    private final AppealService appealService;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendAppeal(@RequestBody @Valid AppealRequest appeal,
                           String currentUsername) {
        appealService.createAppeal(appeal, currentUsername);
    }

    @GetMapping
    public List<ListUserAppealResponse> getCurrentUserAppeals(Pageable pageable,
                                                              String currentUsername) {
        List<Appeal> currentUserAppeals = appealService.getAppealsByUsername(currentUsername, pageable);
        return currentUserAppeals.stream().map(ListUserAppealResponse::fromAppeal).collect(Collectors.toList());
    }

    @GetMapping("/{uuid}")
    public FullUserAppealResponse getAppealForUser(@PathVariable UUID uuid,
                                                   String currentUsername) throws ApiException {
        Optional<Appeal> appeal = appealService.findAppealByUsernameAndId(currentUsername, uuid);
        return appeal.map(FullUserAppealResponse::fromAppeal)
                .orElseThrow(() -> new ApiException(ApiExceptionCode.NOT_FOUND_USER_APPEAL));
    }
}
