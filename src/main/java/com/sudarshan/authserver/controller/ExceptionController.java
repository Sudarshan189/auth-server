package com.sudarshan.authserver.controller;

import com.sudarshan.authserver.dto.ExceptionDto;
import com.sudarshan.authserver.exception.UserException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ExceptionDto> catchUserException(UserException userException) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(userException.getMessage());
        exceptionDto.setStatus("FAILED");
        exceptionDto.setTime(LocalDateTime.now().toString());
        return new ResponseEntity<ExceptionDto>(exceptionDto, userException.getHttpStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDto> catchRunTimeException(RuntimeException runtimeException) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(runtimeException.getMessage());
        exceptionDto.setStatus("FAILED");
        exceptionDto.setTime(LocalDateTime.now().toString());
        return new ResponseEntity<ExceptionDto>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
