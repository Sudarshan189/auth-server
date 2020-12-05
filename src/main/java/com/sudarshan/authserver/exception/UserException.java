package com.sudarshan.authserver.exception;

import org.springframework.http.HttpStatus;

public class UserException extends  RuntimeException{

    private HttpStatus httpStatus;

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
