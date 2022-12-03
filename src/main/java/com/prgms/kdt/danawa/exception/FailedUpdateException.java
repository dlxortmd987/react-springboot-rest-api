package com.prgms.kdt.danawa.exception;

public class FailedUpdateException extends RuntimeException {

    private final int expectedSize;

    private final int actualSize;

    public FailedUpdateException(int expectedSize, int actualSize) {
        super("Incorrect result size: expected " + expectedSize);
        this.expectedSize = expectedSize;
        this.actualSize = actualSize;
    }
}
