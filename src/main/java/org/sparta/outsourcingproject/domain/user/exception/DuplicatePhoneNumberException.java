package org.sparta.outsourcingproject.domain.user.exception;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.ConflictException;

public class DuplicatePhoneNumberException extends ConflictException {
    public DuplicatePhoneNumberException(ErrorCode errorCode){
        super(errorCode);
    }
}
