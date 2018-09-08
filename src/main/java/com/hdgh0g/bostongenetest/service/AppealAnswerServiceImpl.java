package com.hdgh0g.bostongenetest.service;

import com.hdgh0g.bostongenetest.domain.AppealAnswer;
import com.hdgh0g.bostongenetest.repositories.AppealAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AppealAnswerServiceImpl implements AppealAnswerService {

    private final AppealAnswerRepository appealAnswerRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AppealAnswer createAnswer(AppealAnswer answer) {
        return appealAnswerRepository.save(answer);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void removeAnswerById(UUID id) {
        appealAnswerRepository.delete(id);
    }
}
