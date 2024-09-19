package org.sparta.outsourcingproject.common.exception.custom;

import org.sparta.outsourcingproject.common.code.ErrorCode;

public class ConflictException extends RuntimeException {

    public ConflictException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
