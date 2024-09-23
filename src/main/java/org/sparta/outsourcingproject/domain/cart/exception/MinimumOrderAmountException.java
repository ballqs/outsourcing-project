package org.sparta.outsourcingproject.domain.cart.exception;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.BadRequestException;

public class MinimumOrderAmountException extends BadRequestException {
    public MinimumOrderAmountException(ErrorCode errorCode) {
        super(errorCode);
    }
}
