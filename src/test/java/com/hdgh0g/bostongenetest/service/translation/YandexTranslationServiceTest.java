package com.hdgh0g.bostongenetest.service.translation;

import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class YandexTranslationServiceTest {

    @TestConfiguration
    public static class Config {

        @Bean
        public YandexTranslationService yandexTranslationService(RestTemplate template) {
            return new YandexTranslationService(template);
        }

    }

    @Autowired
    private YandexTranslationService yandexTranslationService;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void testDetectLanguage() {
        String text = RandomStringUtils.randomAlphabetic(500);
        String testLang = RandomStringUtils.randomAlphabetic(2);
        YandexTranslationService.DetectResponse response = getDetectResponse(testLang, HttpStatus.OK);
        Mockito.doReturn(response).when(restTemplate)
                .postForObject(Mockito.anyString(), Mockito.any(MultiValueMap.class), Mockito.eq(YandexTranslationService.DetectResponse.class));

        Optional<String> lang = yandexTranslationService.detectLanguage(text);

        MatcherAssert.assertThat(lang.isPresent(), Matchers.is(true));
        MatcherAssert.assertThat(lang.get(), Matchers.is(testLang));
    }

    @Test
    public void testDetectWithEmptyResponse() {
        String text = RandomStringUtils.randomAlphabetic(500);

        Optional<String> lang = yandexTranslationService.detectLanguage(text);

        MatcherAssert.assertThat(lang.isPresent(), Matchers.is(false));
    }

    @Test
    public void testDetectWithWrongCode() {
        String text = RandomStringUtils.randomAlphabetic(500);
        String testLang = RandomStringUtils.randomAlphabetic(2);
        YandexTranslationService.DetectResponse response = getDetectResponse(testLang, HttpStatus.BAD_REQUEST);
        Mockito.doReturn(response).when(restTemplate)
                .postForObject(Mockito.anyString(), Mockito.any(MultiValueMap.class), Mockito.eq(YandexTranslationService.DetectResponse.class));

        Optional<String> lang = yandexTranslationService.detectLanguage(text);

        MatcherAssert.assertThat(lang.isPresent(), Matchers.is(false));
    }

    private YandexTranslationService.DetectResponse getDetectResponse(String testLang, HttpStatus status) {
        YandexTranslationService.DetectResponse response = new YandexTranslationService.DetectResponse();
        response.setCode(status.value());
        response.setLang(testLang);
        return response;
    }

    @Test
    public void testTranslation() {
        String text = RandomStringUtils.randomAlphabetic(500);
        String translation = RandomStringUtils.randomAlphabetic(500);
        String sourceLang = RandomStringUtils.randomAlphabetic(2);
        String targetLang = RandomStringUtils.randomAlphabetic(2);
        YandexTranslationService.TranslateResponse response = getTranslateResponse(translation);
        Mockito.doReturn(response).when(restTemplate)
                .postForObject(Mockito.anyString(), Mockito.any(MultiValueMap.class), Mockito.eq(YandexTranslationService.TranslateResponse.class));

        Optional<String> translated = yandexTranslationService.translateText(text, sourceLang, targetLang);

        MatcherAssert.assertThat(translated.isPresent(), Matchers.is(true));
        MatcherAssert.assertThat(translated.get(), Matchers.is(translation));
    }

    @Test
    public void testEmptyTranslationResponse() {
        String text = RandomStringUtils.randomAlphabetic(500);
        String sourceLang = RandomStringUtils.randomAlphabetic(2);
        String targetLang = RandomStringUtils.randomAlphabetic(2);

        Optional<String> translated = yandexTranslationService.translateText(text, sourceLang, targetLang);

        MatcherAssert.assertThat(translated.isPresent(), Matchers.is(false));
    }

    private YandexTranslationService.TranslateResponse getTranslateResponse(String translation) {
        YandexTranslationService.TranslateResponse response = new YandexTranslationService.TranslateResponse();
        response.setCode(HttpStatus.OK.value());
        response.setText(Collections.singletonList(translation));
        return response;
    }
}