package org.example.han.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.example.han.common.exception.InvalidParameterException;
import org.springframework.http.HttpStatus;
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

    // 비즈니스 오류
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidParameterException.class)
    public CommonResponse onException(InvalidParameterException exception) {
        return CommonResponse.successWithError(exception.getCustomError());
    }

    // 요청 데이터의 유효성 실패
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public CommonResponse onException(MethodArgumentNotValidException exception) {
        return CommonResponse.fail(
                String.valueOf(HttpStatus.BAD_REQUEST.value())
                , exception.getBindingResult().getFieldErrors().stream().map(e -> e.getDefaultMessage()).toList().toString()
        );
    }

    // 요청 데이터의 타입이 안맞는 경우
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class
    })
    public CommonResponse onException(MethodArgumentTypeMismatchException exception) {
        log.error("error : {}", exception.getMessage());
        return CommonResponse.fail(
                String.valueOf(HttpStatus.BAD_REQUEST.value())
                , exception.getMessage());
    }

    // 요청 json 이 규격에 맞지 않은 경우
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({
            HttpMediaTypeNotAcceptableException.class
    })
    public CommonResponse onException(HttpMediaTypeNotAcceptableException exception) {
        log.error("error : {}", exception.getMessage());
        return CommonResponse.fail(
                String.valueOf(HttpStatus.NOT_ACCEPTABLE.value())
                , exception.getMessage());
    }

    // http method 가 맞지 않는 경우
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class
    })
    public CommonResponse onException(HttpRequestMethodNotSupportedException exception) {
        log.error("error : {}", exception.getMessage());
        return CommonResponse.fail(
                String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value())
                , exception.getMessage());
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
        return CommonResponse.fail(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())
                , exception.getMessage());
    }

}
