package com.hdgh0g.bostongenetest.api.v1.responses;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiErrorResponse {

    private final String message;
    private final int code;

    public ApiErrorResponse(HttpStatus code) {
        this.message = code.getReasonPhrase();
        this.code = code.value();
    }
}
