package com.hdgh0g.bostongenetest.repositories;

import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.AppealAnswer;
import com.hdgh0g.bostongenetest.domain.AppealStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppealRepository extends CrudRepository<Appeal, UUID> {

    List<Appeal> findAllByUsername(String username, Pageable pageable);

    Optional<Appeal> findOneByUsernameAndId(String username, UUID id);

    List<Appeal> findAllByStatus(AppealStatus status, Pageable pageable);

    Optional<Appeal> findOneById(UUID id);

    @Modifying
    @Query("update Appeal a " +
            "set answer = :answer, " +
            "status = 'CLOSED' " +
            "where a.id = :id " +
            "and a.answer = null")
    int setAnswerByAppealId(@Param("id") UUID appealId,
                             @Param("answer") AppealAnswer answer);
}
