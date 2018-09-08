package com.hdgh0g.bostongenetest.repositories;

import com.hdgh0g.bostongenetest.domain.AppealAnswer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppealAnswerRepository extends CrudRepository<AppealAnswer, UUID> {

    @Query("select aa from AppealAnswer aa " +
            "where aa.translation is null " +
            "and aa.appeal.translation is not null")
    List<AppealAnswer> findAllWhereTranslationIsNull();

}
