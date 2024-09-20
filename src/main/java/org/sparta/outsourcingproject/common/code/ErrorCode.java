package org.sparta.outsourcingproject.common.code;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    MISMATCH_PASSWORD_ERROR(HttpStatus.FORBIDDEN.value(), "비밀번호가 일치하지 않습니다."),

    USER_NOT_FIND_ERROR(HttpStatus.NOT_FOUND.value(), "유저가 없습니다."),

    DUPLICATE_EMAIL_ERROR(HttpStatus.CONFLICT.value(),"중복된 이메일입니다."),
    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(),"서버 에러"),
    STORE_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "해당 ID를 가진 가게를 찾을 수 없습니다");


    private final int status;
    private final String message;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
}