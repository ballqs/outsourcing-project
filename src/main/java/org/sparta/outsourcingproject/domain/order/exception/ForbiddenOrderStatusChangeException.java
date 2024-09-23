package org.sparta.outsourcingproject.domain.order.exception;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.ForbiddenException;

public class ForbiddenOrderStatusChangeException extends ForbiddenException {
    public ForbiddenOrderStatusChangeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
