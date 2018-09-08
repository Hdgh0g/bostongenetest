package com.hdgh0g.bostongenetest.service;

import com.hdgh0g.bostongenetest.api.v1.requests.AppealRequest;
import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.repositories.AppealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {

    private final AppealRepository appealRepository;

    @Override
    public void createAppeal(AppealRequest appealRequest, String currentUsername) {
        Appeal appeal = Appeal.fromRequest(appealRequest);
        appeal.setUsername(currentUsername);
        appealRepository.save(appeal);
    }

    @Override
    public List<Appeal> getAppealsByUsername(String currentUsername, Pageable pageable) {
        Sort sortByCreationDate = new Sort("creationDateTime");
        new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sortByCreationDate);
        return appealRepository.findAllByUsername(currentUsername, pageable);
    }

    @Override
    public Optional<Appeal> findAppealByUsernameAndId(String currentUsername, UUID uuid) {
        return appealRepository.findOneByUsernameAndId(currentUsername, uuid);
    }
}
