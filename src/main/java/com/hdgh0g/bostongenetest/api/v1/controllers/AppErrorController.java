package com.hdgh0g.bostongenetest.api.v1.controllers;

import com.hdgh0g.bostongenetest.api.v1.responses.ApiErrorResponse;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = AppErrorController.ERROR_PATH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AppErrorController implements ErrorController {

    public static final String ERROR_PATH = "/error";

    @RequestMapping
    public ResponseEntity<ApiErrorResponse> error(HttpServletRequest request) {
        HttpStatus errorStatus = getStatus(request);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(errorStatus);
        return ResponseEntity.status(errorStatus).body(apiErrorResponse);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
