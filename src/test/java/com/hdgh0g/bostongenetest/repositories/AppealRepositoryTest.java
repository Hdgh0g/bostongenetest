package com.hdgh0g.bostongenetest.repositories;

import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.AppealStatus;
import com.hdgh0g.bostongenetest.domain.Translation;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.apache.commons.lang.RandomStringUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureEmbeddedDatabase
public class AppealRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AppealRepository appealRepository;

    @Test
    public void testFindAllByStatusIn() {
        createAppealsInAllStatuses();
        List<AppealStatus> statuses = Collections.singletonList(AppealStatus.OPEN);

        List<Appeal> allByStatusIn = appealRepository.findAllByStatusIn(statuses, new PageRequest(0, Integer.MAX_VALUE));

        boolean onlyOpenAppeals = allByStatusIn.stream().allMatch(appeal -> appeal.getStatus() == AppealStatus.OPEN);
        MatcherAssert.assertThat(onlyOpenAppeals, Matchers.is(true));
    }

    @Test
    public void testFindByAllStatuses() {
        createAppealsInAllStatuses();
        List<AppealStatus> statuses = Arrays.asList(AppealStatus.values());

        List<Appeal> allByStatusIn = appealRepository.findAllByStatusIn(statuses, new PageRequest(0, Integer.MAX_VALUE));

        Set<AppealStatus> foundStatuses = allByStatusIn.stream().map(Appeal::getStatus).collect(Collectors.toSet());
        MatcherAssert.assertThat(foundStatuses.size(), Matchers.is(statuses.size()));
    }

    @Test
    public void testFindByEmptyStatuses() {
        createAppealsInAllStatuses();

        List<Appeal> allByStatusIn = appealRepository.findAllByStatusIn(Collections.emptyList(), new PageRequest(0, Integer.MAX_VALUE));

        MatcherAssert.assertThat(allByStatusIn, Matchers.empty());
    }

    @Test
    public void testFindAllWithEmptyTranslation() {
        Appeal appealWithTranslation = testAppeal();
        appealWithTranslation.setTranslation(testTranslation());
        testEntityManager.persist(appealWithTranslation);
        Appeal appealWithoutTranslation = testAppeal();
        testEntityManager.persist(appealWithoutTranslation);

        List<Appeal> appealsWithoutTranslations = appealRepository.findAllWhereTranslationIsNull();

        MatcherAssert.assertThat(appealsWithoutTranslations, Matchers.not(Matchers.empty()));
        MatcherAssert.assertThat(appealsWithoutTranslations.get(0).getText(), Matchers.is(appealWithoutTranslation.getText()));
    }

    private void createAppealsInAllStatuses() {
        Appeal openStatusAppeal = testAppeal();
        testEntityManager.persist(openStatusAppeal);
        Appeal closedStatusAppeal = testAppeal();
        closedStatusAppeal.setStatus(AppealStatus.CLOSED);
        testEntityManager.persist(closedStatusAppeal);
    }

    public static Appeal testAppeal() {
        Appeal appeal = new Appeal();
        appeal.setText(RandomStringUtils.randomAlphabetic(500));
        appeal.setUsername(RandomStringUtils.randomAlphabetic(30));
        appeal.setStatus(AppealStatus.OPEN);
        return appeal;
    }

    public static Translation testTranslation() {
        return new Translation(
                RandomStringUtils.randomAlphabetic(2),
                RandomStringUtils.randomAlphabetic(2),
                RandomStringUtils.randomAlphabetic(500)
        );
    }
}