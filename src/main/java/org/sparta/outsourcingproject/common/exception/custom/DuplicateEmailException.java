package org.sparta.outsourcingproject.common.exception.custom;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String msg) {
        super(msg);
    }
}
