package com.hdgh0g.bostongenetest.repositories;

import com.hdgh0g.bostongenetest.domain.AppealAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppealAnswerRepository extends CrudRepository<AppealAnswer, UUID> {
}
