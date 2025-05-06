package com.tcs.technicaltest.exception;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;

public class CustomResponseStatusException extends NestedRuntimeException {

    private final String customStatusCode;
    private final HttpStatus httpStatus;

    public CustomResponseStatusException(String customStatusCode, HttpStatus httpStatus, String message) {
        super(message);
        this.customStatusCode = customStatusCode;
        this.httpStatus = httpStatus;
    }

    public String getCustomStatusCode() {
        return customStatusCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}