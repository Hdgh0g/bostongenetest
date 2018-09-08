package com.hdgh0g.bostongenetest.api.v1.controllers;

import com.hdgh0g.bostongenetest.api.v1.responses.ApiErrorResponse;
import com.hdgh0g.bostongenetest.exceptions.ApiException;
import com.hdgh0g.bostongenetest.exceptions.ApiExceptionCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException exception) {
        ApiExceptionCode apiExceptionCode = exception.getCode();
        return ResponseEntity
                .status(apiExceptionCode.getHttpCode())
                .body(new ApiErrorResponse(resolveMessage(apiExceptionCode), apiExceptionCode.ordinal()));
    }

    private String resolveMessage(ApiExceptionCode apiExceptionCode) {
        return null;
    }
}
