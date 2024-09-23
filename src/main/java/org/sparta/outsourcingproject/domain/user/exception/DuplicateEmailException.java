package org.sparta.outsourcingproject.domain.user.exception;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.ConflictException;

public class DuplicateEmailException extends ConflictException {
    public DuplicateEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}