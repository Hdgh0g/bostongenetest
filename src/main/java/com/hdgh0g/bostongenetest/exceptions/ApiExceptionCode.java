package com.hdgh0g.bostongenetest.exceptions;

import lombok.Getter;

public enum  ApiExceptionCode {

    NOT_FOUND_USER_APPEAL(404);

    @Getter
    private final int httpCode;

    ApiExceptionCode(int httpCode) {
        this.httpCode = httpCode;
    }
}
