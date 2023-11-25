package org.example.han.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.example.han.common.exception.InvalidParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidParameterException.class)
    public CommonResponse onException(InvalidParameterException exception) {
        return CommonResponse.success(exception.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MethodArgumentNotValidException.class
            , MethodArgumentTypeMismatchException.class
            , HttpMediaTypeNotAcceptableException.class
            , HttpRequestMethodNotSupportedException.class
    })
    public CommonResponse onException(IllegalArgumentException exception) {
        log.error("error : {}", exception.getMessage());
        return CommonResponse.success(exception.getMessage());
    }



    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public CommonResponse onException(AccessDeniedException exception) {
        return CommonResponse.success(exception.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            IllegalStateException.class
            , IllegalArgumentException.class
            , Exception.class
    })
    public CommonResponse onException(Exception exception) {
        log.error("error : {}", exception.getMessage());
        return CommonResponse.fail(exception.getMessage());
    }

}
