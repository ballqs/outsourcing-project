package org.sparta.outsourcingproject.domain.user.exception;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.NotFoundException;

public class UserNotFindException extends NotFoundException {
    public UserNotFindException(ErrorCode errorCode) {super(errorCode);
    }
}