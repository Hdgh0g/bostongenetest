package com.hdgh0g.bostongenetest.service;

import com.hdgh0g.bostongenetest.domain.AppealAnswer;

import java.util.UUID;

public interface AppealAnswerService {

    AppealAnswer createAnswer(AppealAnswer answer);

    void removeAnswerById(UUID id);
}
