package com.hdgh0g.bostongenetest.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiException extends Exception {

    @Getter
    private final ApiExceptionCode code;
}
