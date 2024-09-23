package org.sparta.outsourcingproject.domain.cart.exception;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.ForbiddenException;

public class CartUnauthorizedException extends ForbiddenException {
    public CartUnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
