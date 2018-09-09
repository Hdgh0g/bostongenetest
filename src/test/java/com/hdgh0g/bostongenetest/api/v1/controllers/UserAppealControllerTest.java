package com.hdgh0g.bostongenetest.api.v1.controllers;

import com.hdgh0g.bostongenetest.api.v1.requests.AppealRequest;
import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.AppealAnswer;
import com.hdgh0g.bostongenetest.domain.Translation;
import com.hdgh0g.bostongenetest.repositories.AppealAnswerRepositoryTest;
import com.hdgh0g.bostongenetest.repositories.AppealRepositoryTest;
import com.hdgh0g.bostongenetest.security.SecurityConfig;
import com.hdgh0g.bostongenetest.service.AppealService;
import com.hdgh0g.bostongenetest.service.MessagePropertiesService;
import com.hdgh0g.bostongenetest.testutils.web.WebTestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@WebMvcTest(UserAppealController.class)
@MockBean(MessagePropertiesService.class)
@Import(SecurityConfig.class)
@EnableSpringDataWebSupport
public class UserAppealControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AppealService appealService;

    @Test
    @WithMockUser
    public void testCreateAppeal() {
        AppealRequest request = new AppealRequest();
        request.setText(RandomStringUtils.randomAlphabetic(500));

        WebTestUtils.given(mvc)
                .body(request)
                .post(UserAppealController.PATH)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        Mockito.verify(appealService).createAppeal(org.mockito.Matchers.eq(request), org.mockito.Matchers.eq("user"));
    }

    @Test
    @WithMockUser
    public void testUserAppeals() {
        WebTestUtils.given(mvc)
                .get(UserAppealController.PATH)
                .then()
                .statusCode(HttpStatus.OK.value());

        Mockito.verify(appealService)
                .getAppealsByUsername(Mockito.eq("user"), Mockito.any());
    }

    @Test
    @WithMockUser
    public void testAppealForUser() {
        UUID uuid = UUID.randomUUID();
        Appeal appeal = AppealRepositoryTest.testAppeal();
        AppealAnswer answer = AppealAnswerRepositoryTest.testAnswer();
        Translation translation = AppealRepositoryTest.testTranslation();
        appeal.setAnswer(answer);
        answer.setTranslation(translation);
        Mockito.doReturn(Optional.of(appeal)).when(appealService).findAppealByUsernameAndId("user", uuid);

        WebTestUtils.given(mvc)
                .get(UserAppealController.PATH + "/{uuid}", uuid)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("answer", Matchers.is(appeal.getAnswer().getTranslation().getTranslatedText()));
    }

    @Test
    @WithMockUser
    public void testNotExistingAppealForUser() {
        Mockito.doReturn(Optional.empty()).when(appealService).findAppealByUsernameAndId(Mockito.any(), Mockito.any());

        WebTestUtils.given(mvc)
                .get(UserAppealController.PATH + "/{uuid}", UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}