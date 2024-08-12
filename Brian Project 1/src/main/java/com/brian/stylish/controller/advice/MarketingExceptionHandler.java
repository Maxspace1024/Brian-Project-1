package com.brian.stylish.controller.advice;

import com.brian.stylish.controller.MarketingController;
import com.brian.stylish.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = MarketingController.class)
public class MarketingExceptionHandler {
    @Autowired
    public ResponseUtils responseUtils;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        return responseUtils.responseError(HttpStatus.BAD_REQUEST, e.toString());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        e.printStackTrace();
        return responseUtils.responseError(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        e.printStackTrace();
        return responseUtils.responseError(HttpStatus.INTERNAL_SERVER_ERROR, "call to website manager");
    }
}
