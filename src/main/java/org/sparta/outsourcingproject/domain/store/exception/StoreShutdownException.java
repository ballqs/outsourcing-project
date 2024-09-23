package org.sparta.outsourcingproject.domain.store.exception;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.ConflictException;

public class StoreShutdownException extends ConflictException {
    public StoreShutdownException(ErrorCode errorCode) {
        super(errorCode);
    }
}
