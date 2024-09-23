package org.sparta.outsourcingproject.common.exception.custom;

import lombok.Getter;
import org.sparta.outsourcingproject.common.code.ErrorCode;

@Getter
public class ForbiddenException extends RuntimeException {

    private int status;

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
    }
}
