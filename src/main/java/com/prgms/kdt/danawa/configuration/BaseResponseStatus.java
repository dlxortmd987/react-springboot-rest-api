package com.prgms.kdt.danawa.configuration;

public enum BaseResponseStatus {
    REQUEST_SUCCESS(200, "Request Success!"),

    DATA_ACCESS_EXCEPTION(501, "Database Access Failed.");

    private final int code;
    private final String message;


    BaseResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
