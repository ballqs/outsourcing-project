package org.sparta.outsourcingproject.domain.menu.exception;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.ConflictException;

public class MenuAlreadyExistsException extends ConflictException {
    public MenuAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
