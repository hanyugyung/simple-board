package org.example.han.common.exception;

import lombok.Getter;
import org.example.han.interfaces.CommonResponse;

public class InvalidParameterException extends RuntimeException {

    private CommonResponse.CustomError customError;

    public InvalidParameterException(CommonResponse.CustomError errorMessage) {
        super(errorMessage.getErrorMessage());
        customError = errorMessage;
    }

    public CommonResponse.CustomError getCustomError() {
        return this.customError;
    }
}
