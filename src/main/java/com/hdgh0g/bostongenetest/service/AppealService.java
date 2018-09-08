package com.hdgh0g.bostongenetest.service;

import com.hdgh0g.bostongenetest.api.v1.requests.AnswerRequest;
import com.hdgh0g.bostongenetest.api.v1.requests.AppealRequest;
import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.AppealStatus;
import com.hdgh0g.bostongenetest.exceptions.ApiException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppealService {

    void createAppeal(AppealRequest appealRequest, String username);

    List<Appeal> getAppealsByUsername(String username, Pageable pageable);

    Optional<Appeal> findAppealByUsernameAndId(String username, UUID uuid);

    List<Appeal> getAllAppealsByStatus(AppealStatus status, Pageable pageable);

    Optional<Appeal> findAppealById(UUID uuid);

    void addAnswer(AnswerRequest answer, String username) throws ApiException;
}
