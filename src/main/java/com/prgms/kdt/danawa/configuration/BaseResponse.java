package com.prgms.kdt.danawa.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import static com.prgms.kdt.danawa.configuration.BaseResponseStatus.REQUEST_SUCCESS;

@JsonPropertyOrder({"code", "message", "result"})
public class BaseResponse<T> {

    private final int code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public BaseResponse(T result) {
        this.code = REQUEST_SUCCESS.getCode();
        this.message = REQUEST_SUCCESS.getMessage();
        this.result = result;
    }

    public BaseResponse(BaseResponseStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
