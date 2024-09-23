package org.sparta.outsourcingproject.domain.store.exception;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.ConflictException;

public class TooManyStoresRegisteredException extends ConflictException {
    public TooManyStoresRegisteredException(ErrorCode errorCode) {
        super(errorCode);
    }
}
