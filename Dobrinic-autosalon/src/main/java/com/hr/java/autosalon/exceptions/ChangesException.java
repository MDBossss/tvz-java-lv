package com.hr.java.autosalon.exceptions;

/**
 * Changes Exception
 */
public class ChangesException extends RuntimeException{
    public ChangesException() {
    }

    public ChangesException(String message) {
        super(message);
    }

    public ChangesException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChangesException(Throwable cause) {
        super(cause);
    }

    public ChangesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
