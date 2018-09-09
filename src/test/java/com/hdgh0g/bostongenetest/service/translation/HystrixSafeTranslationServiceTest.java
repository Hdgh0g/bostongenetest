package com.hdgh0g.bostongenetest.service.translation;

import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class HystrixSafeTranslationServiceTest {

    @TestConfiguration
    @EnableCircuitBreaker //hystrix activation
    @EnableAspectJAutoProxy //hystrix uses aop for its operations
    public static class Config {

        @Bean
        public HystrixSafeTranslationService hystrixSafeTranslationService(YandexTranslationService mockTranslationService) {
            return new HystrixSafeTranslationService(mockTranslationService);
        }

    }

    @MockBean
    private YandexTranslationService mockTranslationService;

    @Autowired
    private TranslationService hystrixSafeTranslationService;

    private static final Answer<Optional<String>> SLOW_TEXT_ANSWER = invocation -> {
        // TODO: 9/9/2018 Config this TIMEOUT separately for tests and app
        Thread.sleep(Long.valueOf(HystrixSafeTranslationService.TIMEOUT) + 100);
        return Optional.of(RandomStringUtils.randomAlphabetic(100));
    };

    @Test
    public void testDetectDelegation() {
        String text = RandomStringUtils.randomAlphabetic(500);
        String testLang = RandomStringUtils.randomAlphabetic(2);
        Mockito.doReturn(Optional.of(testLang)).when(mockTranslationService).detectLanguage(text);

        Optional<String> lang = hystrixSafeTranslationService.detectLanguage(text);

        MatcherAssert.assertThat(lang.isPresent(), Matchers.is(true));
        MatcherAssert.assertThat(lang.get(), Matchers.is(testLang));
    }

    @Test
    public void testTranslateDelegation() {
        String text = RandomStringUtils.randomAlphabetic(500);
        String translation = RandomStringUtils.randomAlphabetic(500);
        String sourceLang = RandomStringUtils.randomAlphabetic(2);
        String targetLang = RandomStringUtils.randomAlphabetic(2);
        Mockito.doReturn(Optional.of(translation)).when(mockTranslationService).translateText(text, sourceLang, targetLang);

        Optional<String> translated = hystrixSafeTranslationService.translateText(text, sourceLang, targetLang);

        MatcherAssert.assertThat(translated.isPresent(), Matchers.is(true));
        MatcherAssert.assertThat(translated.get(), Matchers.is(translation));
    }

    @Test
    public void testTooLongDetect() {
        Mockito.when(mockTranslationService.detectLanguage(Mockito.any())).thenAnswer(SLOW_TEXT_ANSWER);

        Optional<String> lang = hystrixSafeTranslationService.detectLanguage(RandomStringUtils.randomAlphabetic(500));

        MatcherAssert.assertThat(lang.isPresent(), Matchers.is(false));
    }

    @Test
    public void testTooLongTranslation() {
        Mockito.when(mockTranslationService.translateText(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenAnswer(SLOW_TEXT_ANSWER);

        Optional<String> lang = hystrixSafeTranslationService.translateText(
                RandomStringUtils.randomAlphabetic(500),
                RandomStringUtils.randomAlphabetic(500),
                RandomStringUtils.randomAlphabetic(500)
        );

        MatcherAssert.assertThat(lang.isPresent(), Matchers.is(false));
    }

    @Test
    public void testDetectException() {

    }

    @Test
    public void testTranslationException() {

    }
}