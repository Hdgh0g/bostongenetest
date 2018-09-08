package com.hdgh0g.bostongenetest.repositories;

import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.AppealStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
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

    Optional<Appeal> findOneByIdAndStatus(UUID id, AppealStatus status);

    @Query("select a from Appeal a where a.translation is null")
    List<Appeal> findAllWhereTranslationIsNull();
}
