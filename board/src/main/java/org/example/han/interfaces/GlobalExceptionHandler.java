package org.example.han.interfaces;

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
    @ExceptionHandler({IllegalArgumentException.class
            , MethodArgumentNotValidException.class
            , MethodArgumentTypeMismatchException.class
            , HttpMediaTypeNotAcceptableException.class
            , HttpRequestMethodNotSupportedException.class
    })
    public CommonResponse onException(IllegalArgumentException exception) {
        return CommonResponse.fail(exception.getMessage());
    }



    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public CommonResponse onException(AccessDeniedException exception) {
        return CommonResponse.fail(exception.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            IllegalStateException.class
            , Exception.class
    })
    public CommonResponse onException(Exception exception) {
        return CommonResponse.fail(exception.getMessage());
    }

}
