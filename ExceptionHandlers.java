package com.heiyu.messaging.Advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception) {
        exception.printStackTrace();
        return exception.getMessage();
    }
}
