package org.sparta.outsourcingproject.domain.menu.exception;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.ForbiddenException;

public class AuthorityMismatchException extends ForbiddenException {
    public AuthorityMismatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
