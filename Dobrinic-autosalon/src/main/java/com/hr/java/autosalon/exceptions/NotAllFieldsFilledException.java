package com.hr.java.autosalon.exceptions;

/**
 * Not All Fields Filled Exception
 */
public class NotAllFieldsFilledException extends Exception{
    public NotAllFieldsFilledException() {
    }

    public NotAllFieldsFilledException(String message) {
        super(message);
    }

    public NotAllFieldsFilledException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAllFieldsFilledException(Throwable cause) {
        super(cause);
    }

    public NotAllFieldsFilledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
