package com.hdgh0g.bostongenetest.service;

import com.hdgh0g.bostongenetest.api.v1.requests.AnswerRequest;
import com.hdgh0g.bostongenetest.api.v1.requests.AppealRequest;
import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.AppealStatus;
import com.hdgh0g.bostongenetest.exceptions.ApiException;
import com.hdgh0g.bostongenetest.exceptions.ApiExceptionCode;
import com.hdgh0g.bostongenetest.repositories.AppealRepository;
import com.hdgh0g.bostongenetest.testutils.matchers.ApiExceptionMatcher;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
public class AppealServiceImplTest {

    @TestConfiguration
    public static class Config {

        @Bean
        public AppealServiceImpl appealService(AppealRepository appealRepository) {
            return new AppealServiceImpl(appealRepository);
        }
    }

    @MockBean
    private AppealRepository appealRepository;

    @Autowired
    private AppealServiceImpl appealService;

    @Captor
    private ArgumentCaptor<Appeal> appealArgumentCaptor;

    @Captor
    private ArgumentCaptor<Pageable> pageableArgumentCaptor;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testCreation() {
        String username = RandomStringUtils.randomAlphabetic(20);
        AppealRequest appealRequest = new AppealRequest();
        appealRequest.setText(RandomStringUtils.randomAlphabetic(200));

        appealService.createAppeal(appealRequest, username);

        Mockito.verify(appealRepository).save(appealArgumentCaptor.capture());
        Appeal captured = appealArgumentCaptor.getValue();
        MatcherAssert.assertThat(captured, Matchers.notNullValue());
        MatcherAssert.assertThat(captured.getUsername(), Matchers.is(username));
        MatcherAssert.assertThat(captured.getTranslation(), Matchers.nullValue());
        MatcherAssert.assertThat(captured.getText(), Matchers.is(appealRequest.getText()));
        MatcherAssert.assertThat(captured.getStatus(), Matchers.is(AppealStatus.OPEN));
    }

    @Test
    public void testGetUserAppeals() {
        String username = RandomStringUtils.randomAlphabetic(20);
        PageRequest pageRequest = new PageRequest(0, 1);

        appealService.getAppealsByUsername(username, pageRequest);

        Mockito.verify(appealRepository).findAllByUsername(org.mockito.Matchers.eq(username), pageableArgumentCaptor.capture());
        MatcherAssert.assertThat(pageableArgumentCaptor.getValue().getSort(), Matchers.is(new Sort("creationDateTime")));
    }

    @Test
    public void testFindAppealByUsernameAndId() {
        String username = RandomStringUtils.randomAlphabetic(20);
        UUID id = UUID.randomUUID();
        Mockito.doReturn(Optional.empty()).when(appealRepository).findOneByUsernameAndId(Mockito.any(), Mockito.any());

        Optional<Appeal> appealById = appealService.findAppealByUsernameAndId(username, id);

        Mockito.verify(appealRepository).findOneByUsernameAndId(username, id);
        MatcherAssert.assertThat(appealById.isPresent(), Matchers.is(false));
    }

    @Test
    public void testGetAllAppealsByStatus() {
        List<AppealStatus> statuses = Collections.singletonList(AppealStatus.OPEN);
        PageRequest pageRequest = new PageRequest(0, 1);

        appealService.getAllAppealsByStatus(statuses, pageRequest);

        Mockito.verify(appealRepository).findAllByStatusIn(org.mockito.Matchers.eq(statuses), pageableArgumentCaptor.capture());
        MatcherAssert.assertThat(pageableArgumentCaptor.getValue().getSort(), Matchers.is(new Sort("creationDateTime")));
    }

    @Test
    public void testFindAppealById() {
        UUID id = UUID.randomUUID();
        Mockito.doReturn(Optional.of(new Appeal())).when(appealRepository).findOneById(Mockito.any());

        Optional<Appeal> appealById = appealService.findAppealById(id);

        Mockito.verify(appealRepository).findOneById(id);
        MatcherAssert.assertThat(appealById.isPresent(), Matchers.is(true));
    }

    @Test
    public void testAddAnswer() throws ApiException {
        String username = RandomStringUtils.randomAlphabetic(20);
        AnswerRequest answerRequest = new AnswerRequest();
        UUID id = UUID.randomUUID();
        answerRequest.setAppealId(id);
        answerRequest.setText(RandomStringUtils.randomAlphabetic(200));
        Mockito.doReturn(Optional.of(new Appeal())).when(appealRepository).findOneByIdAndStatus(id, AppealStatus.OPEN);
        Mockito.doReturn(new Appeal()).when(appealRepository).save(Mockito.any(Appeal.class));

        appealService.addAnswer(answerRequest, username);

        Mockito.verify(appealRepository).save(appealArgumentCaptor.capture());
        Appeal updatedAppeal = appealArgumentCaptor.getValue();
        MatcherAssert.assertThat(updatedAppeal, Matchers.notNullValue());
        MatcherAssert.assertThat(updatedAppeal.getAnswer().getText(), Matchers.is(answerRequest.getText()));
        MatcherAssert.assertThat(updatedAppeal.getStatus(), Matchers.is(AppealStatus.CLOSED));
    }

    @Test
    public void testAddAnswerForNotExistingOrClosed() throws ApiException {
        String username = RandomStringUtils.randomAlphabetic(20);
        exceptionRule.expect(ApiException.class);
        exceptionRule.expect(ApiExceptionMatcher.withCode(ApiExceptionCode.ALREADY_ANSWERED));
        Mockito.doReturn(Optional.empty()).when(appealRepository).findOneByIdAndStatus(null, AppealStatus.OPEN);

        appealService.addAnswer(new AnswerRequest(), username);
    }
}