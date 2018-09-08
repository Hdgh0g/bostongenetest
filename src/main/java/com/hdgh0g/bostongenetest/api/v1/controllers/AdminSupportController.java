package com.hdgh0g.bostongenetest.api.v1.controllers;

import com.hdgh0g.bostongenetest.api.v1.requests.AnswerRequest;
import com.hdgh0g.bostongenetest.api.v1.responses.FullAppealResponse;
import com.hdgh0g.bostongenetest.api.v1.responses.ListAppealResponse;
import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.AppealStatus;
import com.hdgh0g.bostongenetest.exceptions.ApiException;
import com.hdgh0g.bostongenetest.exceptions.ApiExceptionCode;
import com.hdgh0g.bostongenetest.service.AppealService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/admin/appeals",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AdminSupportController {

    private final AppealService appealService;

    @GetMapping
    public List<ListAppealResponse> getAppealList(@RequestParam(defaultValue = "OPEN") AppealStatus status,
                                                  Pageable pageable) {
        List<Appeal> appeals = appealService.getAllAppealsByStatus(status, pageable);
        return appeals.stream().map(ListAppealResponse::forUser).collect(Collectors.toList());
    }

    @GetMapping("/{uuid}")
    public FullAppealResponse getAppealInfo(@PathVariable UUID uuid) throws ApiException {
        return appealService.findAppealById(uuid)
                .map(FullAppealResponse::forUser)
                .orElseThrow(() -> new ApiException(ApiExceptionCode.NOT_FOUND_ADMIN_APPEAL));
    }

    @PostMapping("/answer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void postAnswerAndClose(@RequestBody AnswerRequest answer,
                                   String currentUsername) throws ApiException {
        appealService.addAnswer(answer, currentUsername);
    }
}
