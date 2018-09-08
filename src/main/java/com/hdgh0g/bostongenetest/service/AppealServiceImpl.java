package com.hdgh0g.bostongenetest.service;

import com.hdgh0g.bostongenetest.api.v1.requests.AnswerRequest;
import com.hdgh0g.bostongenetest.api.v1.requests.AppealRequest;
import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.AppealAnswer;
import com.hdgh0g.bostongenetest.domain.AppealStatus;
import com.hdgh0g.bostongenetest.exceptions.ApiException;
import com.hdgh0g.bostongenetest.exceptions.ApiExceptionCode;
import com.hdgh0g.bostongenetest.repositories.AppealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {

    private final AppealRepository appealRepository;

    @Override
    public void createAppeal(AppealRequest appealRequest, String username) {
        Appeal appeal = Appeal.fromRequest(appealRequest);
        appeal.setUsername(username);
        appealRepository.save(appeal);
    }

    @Override
    public List<Appeal> getAppealsByUsername(String username, Pageable pageable) {
        pageable = addSortToPageable(pageable);
        return appealRepository.findAllByUsername(username, pageable);
    }

    @Override
    public Optional<Appeal> findAppealByUsernameAndId(String username, UUID uuid) {
        return appealRepository.findOneByUsernameAndId(username, uuid);
    }

    @Override
    public List<Appeal> getAllAppealsByStatus(AppealStatus status, Pageable pageable) {
        pageable = addSortToPageable(pageable);
        return appealRepository.findAllByStatus(status, pageable);
    }

    @Override
    public Optional<Appeal> findAppealById(UUID uuid) {
        return appealRepository.findOneById(uuid);
    }

    @Override
    public void addAnswer(AnswerRequest answer, String username) throws ApiException {
        appealRepository.findOneByIdAndStatus(answer.getAppealId(), AppealStatus.OPEN)
                .map(appeal -> {
                    appeal.setStatus(AppealStatus.CLOSED);
                    appeal.setAnswer(AppealAnswer.fromRequest(answer, username));
                    return appealRepository.save(appeal);
                })
                .orElseThrow(() -> new ApiException(ApiExceptionCode.ALREADY_ANSWERED));
    }

    private Pageable addSortToPageable(Pageable pageable) {
        Sort sortByCreationDate = new Sort("creationDateTime");
        pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sortByCreationDate);
        return pageable;
    }
}
