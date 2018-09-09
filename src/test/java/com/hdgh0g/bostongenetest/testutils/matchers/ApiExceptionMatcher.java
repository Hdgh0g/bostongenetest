package com.hdgh0g.bostongenetest.testutils.matchers;

import com.hdgh0g.bostongenetest.exceptions.ApiException;
import com.hdgh0g.bostongenetest.exceptions.ApiExceptionCode;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ApiExceptionMatcher extends TypeSafeMatcher<ApiException> {

    private final ApiExceptionCode code;

    private ApiExceptionMatcher(ApiExceptionCode code) {
        this.code = code;
    }

    @Override
    protected boolean matchesSafely(ApiException item) {
        return item != null && item.getCode() == this.code;
    }

    @Override
    public void describeTo(Description description) {

    }

    public static ApiExceptionMatcher withCode(ApiExceptionCode code) {
        return new ApiExceptionMatcher(code);
    }
}
