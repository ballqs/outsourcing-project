package org.sparta.outsourcingproject.domain.user.exception;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.BadRequestException;

public class SamePasswordException extends BadRequestException {
    public SamePasswordException(ErrorCode error) {
        super(error);
    }
}
