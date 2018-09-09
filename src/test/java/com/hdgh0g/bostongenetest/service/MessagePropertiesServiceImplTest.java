package com.hdgh0g.bostongenetest.service;

import com.hdgh0g.bostongenetest.config.MessageConfiguration;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Import(MessageConfiguration.class)
public class MessagePropertiesServiceImplTest {

    @TestConfiguration
    public static class Config {

        @Bean
        public MessagePropertiesServiceImpl messagePropertiesService(MessageSource messageSource) {
            return new MessagePropertiesServiceImpl(messageSource);
        }
    }

    @Autowired
    private MessagePropertiesServiceImpl messagePropertiesService;

    @Test
    public void testExistingMessage() {
        String message = messagePropertiesService.getMessage("ApiException.ALREADY_ANSWERED");
        MatcherAssert.assertThat(message, Matchers.notNullValue());
        MatcherAssert.assertThat(message, Matchers.is("Заявка не существует или уже закрыта"));
    }

    @Test(expected = NoSuchMessageException.class)
    public void testNotExistingMessage() {
        messagePropertiesService.getMessage("This one does not exists");
    }
}