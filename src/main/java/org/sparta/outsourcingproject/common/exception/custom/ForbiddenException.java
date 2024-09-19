package org.sparta.outsourcingproject.common.exception.custom;

import org.sparta.outsourcingproject.common.code.ErrorCode;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
