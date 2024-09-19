package org.sparta.outsourcingproject.common.exception.custom;

import org.sparta.outsourcingproject.common.code.ErrorCode;

public class NotFoundException extends RuntimeException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
