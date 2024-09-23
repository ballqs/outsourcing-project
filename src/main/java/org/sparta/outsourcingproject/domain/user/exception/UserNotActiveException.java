package org.sparta.outsourcingproject.domain.user.exception;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.ForbiddenException;

public class UserNotActiveException extends ForbiddenException {
    public UserNotActiveException(ErrorCode errorCode) {
        super(errorCode);
    }
}
