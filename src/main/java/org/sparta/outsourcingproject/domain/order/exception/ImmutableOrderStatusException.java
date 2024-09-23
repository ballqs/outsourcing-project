package org.sparta.outsourcingproject.domain.order.exception;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.BadRequestException;

public class ImmutableOrderStatusException extends BadRequestException {
    public ImmutableOrderStatusException(ErrorCode errorCode) {
        super(errorCode);
    }
}
