package com.hdgh0g.bostongenetest.config;

import com.hdgh0g.bostongenetest.api.v1.argresolvers.CurrentUsernameArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    private final CurrentUsernameArgumentResolver currentUsernameArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUsernameArgumentResolver);
    }

}
