package org.sparta.outsourcingproject.domain.user.exception;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.ForbiddenException;

public class MismatchPasswordException extends ForbiddenException {
    public MismatchPasswordException(ErrorCode errorCode) {super(errorCode);
    }
}
