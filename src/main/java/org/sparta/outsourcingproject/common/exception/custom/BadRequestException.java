package org.sparta.outsourcingproject.common.exception.custom;

import lombok.Getter;
import org.sparta.outsourcingproject.common.code.ErrorCode;

@Getter
public class BadRequestException extends RuntimeException {

    private int status;

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
    }
}
