package com.brian.stylish.controller.advice;

import com.brian.stylish.controller.OrderController;
import com.brian.stylish.exception.NoTokenException;
import com.brian.stylish.exception.WrongTokenException;
import com.brian.stylish.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = OrderController.class)
public class OrderExceptionHandler {

    @Autowired
    private ResponseUtils responseUtils;

    @ExceptionHandler(NoTokenException.class)
    public ResponseEntity<?> handleNoTokenException(NoTokenException e) {
        return responseUtils.responseError(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(WrongTokenException.class)
    public ResponseEntity<?> handleWrongTokenException(WrongTokenException e) {
        return responseUtils.responseError(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        e.printStackTrace();
        return responseUtils.responseError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
