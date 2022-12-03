package com.prgms.kdt.danawa.exception;

import com.prgms.kdt.danawa.configuration.BaseResponseStatus;

public class BaseException extends RuntimeException {

    private final BaseResponseStatus status;

    public BaseException(BaseResponseStatus status) {
        this.status = status;
    }
}
