package com.hdgh0g.bostongenetest.service;

import com.hdgh0g.bostongenetest.api.v1.requests.AppealRequest;
import com.hdgh0g.bostongenetest.domain.Appeal;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppealService {

    void createAppeal(AppealRequest appealRequest, String currentUsername);

    List<Appeal> getAppealsByUsername(String currentUsername, Pageable pageable);

    Optional<Appeal> findAppealByUsernameAndId(String currentUsername, UUID uuid);
}
