package com.hr.java.autosalon.exceptions;

/**
 * Current User Exception
 */
public class CurrentUserException extends RuntimeException{
    public CurrentUserException() {
    }

    public CurrentUserException(String message) {
        super(message);
    }

    public CurrentUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrentUserException(Throwable cause) {
        super(cause);
    }

    public CurrentUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
