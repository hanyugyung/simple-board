package org.example.han.common.exception;

import org.example.han.interfaces.CommonResponse;

public class InvalidParameterException extends RuntimeException {

    public InvalidParameterException(CommonResponse.CustomErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());
    }

}
