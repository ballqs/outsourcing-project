package org.sparta.outsourcingproject.domain.store.exception;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.NotFoundException;

public class StoreClosedException extends NotFoundException {
    public StoreClosedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
