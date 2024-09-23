package org.sparta.outsourcingproject.domain.menu.exception;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.NotFoundException;

public class MenuNotExistsException extends NotFoundException {
    public MenuNotExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}