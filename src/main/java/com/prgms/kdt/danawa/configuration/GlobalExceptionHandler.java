package com.prgms.kdt.danawa.configuration;

import com.prgms.kdt.danawa.configuration.BaseResponse;
import com.prgms.kdt.danawa.configuration.BaseResponseStatus;
import com.prgms.kdt.danawa.exception.FailedUpdateException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.prgms.kdt.danawa.configuration.BaseResponseStatus.DATA_ACCESS_EXCEPTION;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<String> handleRuntimeException(RuntimeException exception) {
        return new BaseResponse<>(501, exception.getMessage());
    }
}
