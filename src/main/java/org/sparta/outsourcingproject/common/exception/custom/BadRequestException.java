package org.sparta.outsourcingproject.common.exception.custom;

import org.sparta.outsourcingproject.common.code.ErrorCode;

public class BadRequestException extends RuntimeException {
    public BadRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
