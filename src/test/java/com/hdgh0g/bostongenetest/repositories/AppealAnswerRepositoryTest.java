package com.hdgh0g.bostongenetest.repositories;

import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.AppealAnswer;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureEmbeddedDatabase
public class AppealAnswerRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AppealAnswerRepository appealAnswerRepository;

    @Test
    public void testFindAllWithoutTranslation() {
        Appeal appeal = AppealRepositoryTest.testAppeal();
        AppealAnswer appealAnswer = testAnswer();
        Translation translation = AppealRepositoryTest.testTranslation();
        appeal.setTranslation(translation);
        appeal.setAnswer(appealAnswer);
        testEntityManager.persist(appeal);

        List<AppealAnswer> answersToTranslate = appealAnswerRepository.findAllWhereTranslationIsNull();

        MatcherAssert.assertThat(answersToTranslate, Matchers.not(Matchers.empty()));
        MatcherAssert.assertThat(answersToTranslate.get(0).getText(), Matchers.is(appealAnswer.getText()));
    }

    @Test
    public void testAppealWithoutTranslation() {
        Appeal appeal = AppealRepositoryTest.testAppeal();
        AppealAnswer appealAnswer = testAnswer();
        appeal.setAnswer(appealAnswer);
        testEntityManager.persist(appeal);

        List<AppealAnswer> answersToTranslate = appealAnswerRepository.findAllWhereTranslationIsNull();

        MatcherAssert.assertThat(answersToTranslate, Matchers.is(Matchers.empty()));
    }

    @Test
    public void testAlreadyTranslatedAnswer() {
        Appeal appeal = AppealRepositoryTest.testAppeal();
        AppealAnswer appealAnswer = testAnswer();
        Translation translation = AppealRepositoryTest.testTranslation();
        appeal.setTranslation(translation);
        appeal.setAnswer(appealAnswer);
        appealAnswer.setTranslation(translation);
        testEntityManager.persist(appeal);

        List<AppealAnswer> answersToTranslate = appealAnswerRepository.findAllWhereTranslationIsNull();

        MatcherAssert.assertThat(answersToTranslate, Matchers.is(Matchers.empty()));
    }

    private AppealAnswer testAnswer() {
        AppealAnswer appealAnswer = new AppealAnswer();
        appealAnswer.setText(RandomStringUtils.randomAlphabetic(500));
        appealAnswer.setUsername(RandomStringUtils.randomAlphabetic(30));
        return appealAnswer;
    }

}