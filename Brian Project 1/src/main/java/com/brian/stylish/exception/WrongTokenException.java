package com.brian.stylish.exception;

public class WrongTokenException extends RuntimeException {
    public WrongTokenException(String message) {
        super(message);
    }

    public WrongTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
