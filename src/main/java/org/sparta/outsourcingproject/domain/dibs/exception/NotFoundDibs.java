package org.sparta.outsourcingproject.domain.dibs.exception;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.NotFoundException;

public class NotFoundDibs extends NotFoundException {
    public NotFoundDibs(ErrorCode errorCode) {
        super(errorCode);
    }
}
