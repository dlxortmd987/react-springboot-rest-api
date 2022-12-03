package com.prgms.kdt.danawa.exception;

public class InvalidStatusException extends RuntimeException {

    private static final String MESSAGE = "You cannot change status. Because current status is %s.";

    public InvalidStatusException(String currentStatus) {
        super(String.format(MESSAGE, currentStatus));
    }

    public InvalidStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
