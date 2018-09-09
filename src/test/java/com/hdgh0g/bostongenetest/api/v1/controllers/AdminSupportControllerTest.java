package com.hdgh0g.bostongenetest.api.v1.controllers;

import com.hdgh0g.bostongenetest.api.v1.requests.AnswerRequest;
import com.hdgh0g.bostongenetest.api.v1.responses.BasicListAppealResponse;
import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.AppealStatus;
import com.hdgh0g.bostongenetest.domain.TranslationStatus;
import com.hdgh0g.bostongenetest.exceptions.ApiException;
import com.hdgh0g.bostongenetest.exceptions.ApiExceptionCode;
import com.hdgh0g.bostongenetest.repositories.AppealRepositoryTest;
import com.hdgh0g.bostongenetest.security.Role;
import com.hdgh0g.bostongenetest.security.SecurityConfig;
import com.hdgh0g.bostongenetest.service.AppealService;
import com.hdgh0g.bostongenetest.service.MessagePropertiesService;
import com.hdgh0g.bostongenetest.testutils.web.WebTestUtils;
import org.apache.commons.lang3.RandomStringUtils;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;


@RunWith(SpringRunner.class)
@WebMvcTest(AdminSupportController.class)
@MockBean(MessagePropertiesService.class)
@Import(SecurityConfig.class)
@EnableSpringDataWebSupport
public class AdminSupportControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AppealService appealService;

    @Test
    @WithMockUser
    public void testAdminEndpointAsUser() {
        WebTestUtils.given(mvc)
                .get(AdminSupportController.PATH)
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void testEndpointWithoutAuthorization() {
        WebTestUtils.given(mvc)
                .get(AdminSupportController.PATH)
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @WithMockUser(roles = Role.ADMIN)
    public void testAppealListWithoutParam() {
        WebTestUtils.given(mvc)
                .get(AdminSupportController.PATH)
                .then()
                .statusCode(HttpStatus.OK.value());

        Mockito.verify(appealService)
                .getAllAppealsByStatus(org.mockito.Matchers.eq(Collections.singletonList(AppealStatus.OPEN)), Mockito.any());
    }

    @Test
    @WithMockUser(roles = Role.ADMIN)
    public void testAppealListWithAllStatuses() {
        Mockito.doReturn(Collections.singletonList(AppealRepositoryTest.testAppeal()))
                .when(appealService).getAllAppealsByStatus(Mockito.any(), Mockito.any());

        WebTestUtils.given(mvc)
                .param("status", (Object[]) AppealStatus.values())
                .get(AdminSupportController.PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("text[0].length()", Matchers.is(BasicListAppealResponse.PREVIEW_TEXT_LIMIT));

        Mockito.verify(appealService)
                .getAllAppealsByStatus(org.mockito.Matchers.eq(Arrays.asList(AppealStatus.values())), Mockito.any());
    }

    @Test
    @WithMockUser(roles = Role.ADMIN)
    public void testAllInfoByAppeal() {
        UUID uuid = UUID.randomUUID();
        Appeal appeal = AppealRepositoryTest.testAppeal();
        appeal.setTranslation(AppealRepositoryTest.testTranslation());
        Mockito.doReturn(Optional.of(appeal)).when(appealService).findAppealById(uuid);

        WebTestUtils.given(mvc)
                .get(AdminSupportController.PATH + "/{uuid}", uuid)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("translationStatus", Matchers.is(TranslationStatus.TRANSLATED.name()))
                .body("username", Matchers.is(appeal.getUsername()))
                .body("text", Matchers.is(appeal.getTranslation().getTranslatedText()));
    }

    @Test
    @WithMockUser(roles = Role.ADMIN)
    public void testAllInfoNotFound() {
        Mockito.doReturn(Optional.empty()).when(appealService).findAppealById(Mockito.any());

        WebTestUtils.given(mvc)
                .get(AdminSupportController.PATH + "/{uuid}", UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @WithMockUser(roles = Role.ADMIN, username = "admin")
    public void testPostAnswer() throws ApiException {
        AnswerRequest request = testAnswerRequest();

        WebTestUtils.given(mvc)
                .body(request)
                .post(AdminSupportController.PATH + "/answer")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        Mockito.verify(appealService).addAnswer(org.mockito.Matchers.eq(request), org.mockito.Matchers.eq("admin"));
    }

    @Test
    @WithMockUser(roles = Role.ADMIN)
    public void testErrorPostAnswer() throws ApiException {
        AnswerRequest request = testAnswerRequest();
        Mockito.doThrow(new ApiException(ApiExceptionCode.ALREADY_ANSWERED)).when(appealService).addAnswer(Mockito.any(), Mockito.any());

        WebTestUtils.given(mvc)
                .body(request)
                .post(AdminSupportController.PATH + "/answer")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private AnswerRequest testAnswerRequest() {
        AnswerRequest request = new AnswerRequest();
        request.setText(RandomStringUtils.randomAlphabetic(500));
        request.setAppealId(UUID.randomUUID());
        return request;
    }
}