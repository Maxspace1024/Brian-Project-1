package com.brian.stylish.exception;

public class NoTokenException extends RuntimeException {
    public NoTokenException(String message) {
        super(message);
    }

    public NoTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
