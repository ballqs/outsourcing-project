package org.sparta.outsourcingproject.domain.store.exception;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.NotFoundException;

public class StoreNotFoundException extends NotFoundException {
    public StoreNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
