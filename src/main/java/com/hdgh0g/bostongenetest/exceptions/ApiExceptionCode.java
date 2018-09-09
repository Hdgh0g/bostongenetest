package com.hdgh0g.bostongenetest.exceptions;

import lombok.Getter;

public enum ApiExceptionCode {

    NOT_FOUND_USER_APPEAL(404),
    NOT_FOUND_ADMIN_APPEAL(404),
    ALREADY_ANSWERED(400);

    @Getter
    private final int httpCode;

    ApiExceptionCode(int httpCode) {
        this.httpCode = httpCode;
    }
}
