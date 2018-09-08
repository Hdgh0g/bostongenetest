package com.hdgh0g.bostongenetest.api.v1.argresolvers;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUsernameArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String PARAM_NAME = "currentUsername";
    private static final Class<String> PARAM_TYPE = String.class;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterName().equals(PARAM_NAME)
                && parameter.getParameterType().equals(PARAM_TYPE);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((User) principal).getUsername();
    }
}
