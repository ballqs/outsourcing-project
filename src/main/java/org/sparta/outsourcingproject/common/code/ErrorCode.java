package org.sparta.outsourcingproject.common.code;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    AUTHORITY_MISMATCH_ERROR(HttpStatus.FORBIDDEN.value(), "올바른 권한을 보유하지 않았습니다."),
    MENU_ALREADYEXISTS_ERROR(HttpStatus.CONFLICT.value(), "중복된 메뉴입니다."),
    MENU_NOTEXISTS_ERROR(HttpStatus.NOT_FOUND.value(), "메뉴가 없습니다."),
    MENU_UNAVAILABLE_ERROR(HttpStatus.NOT_FOUND.value(), "해당 메뉴는 품절되었거나 삭제되었습니다."),

    // 400

    BAD_REQUEST_MINIMUM_ORDER_NOT_MET(HttpStatus.BAD_REQUEST.value(), "최소 주문 금액보다 작습니다."),
    CONFLICT_DELIVERY_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST.value(), "이미 배달완료된 건입니다."),
    SAME_PASSWORD(HttpStatus.BAD_REQUEST.value(), "바꾸려는 비밀번호가 기존과 동일합니다."),
    ORDER_CANCELLED(HttpStatus.BAD_REQUEST.value(), "주문이 취소된 건입니다."),

    // 403
    MISMATCH_PASSWORD_ERROR(HttpStatus.FORBIDDEN.value(), "비밀번호가 일치하지 않습니다."),
    FORBIDDEN_CART_MODIFICATION(HttpStatus.FORBIDDEN.value(), "본인의 장바구니가 아닌 것은 수정 불가능합니다."),
    FORBIDDEN_CART_DELETION(HttpStatus.FORBIDDEN.value(), "본인이 아니면 삭제가 불가능합니다."),
    FORBIDDEN_ORDER_STATUS_CHANGE(HttpStatus.FORBIDDEN.value(), "본인의 가게의 주문이 아니면 상태전환이 불가능합니다."),
    USER_NOT_ACTIVE_ERROR(HttpStatus.FORBIDDEN.value(), "사용자 비활성화 상태입니다."),
    FORBIDDEN_NO_PERMISSION(HttpStatus.FORBIDDEN.value() , "접근 권한이 없습니다."),

    // 404
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 ID를 가진 가게를 찾을 수 없습니다"),
    USER_NOT_FIND_ERROR(HttpStatus.NOT_FOUND.value(), "유저가 없습니다."),
    CART_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "장바구니 정보가 없습니다."),
    CART_DETAIL_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "장바구니 상세 정보가 없습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 주문이 없습니다."),
    DIBS_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당하는 찜이 없습니다."),

    // 409
    DUPLICATE_EMAIL_ERROR(HttpStatus.CONFLICT.value(),"중복된 이메일입니다."),
    TOO_MANY_STORE_REGISTERED(HttpStatus.CONFLICT.value(), "한 명당 최대 3개까지의 가게만 운영할 수 있습니다."),
    DUPLICATE_PHONE_NUMBER_ERROR(HttpStatus.CONFLICT.value(),"중복된 핸드폰번호입니다."),

    // 410(Gone)
    STORE_ALREAY_SHUTDOWN(HttpStatus.GONE.value(), "이미 폐업한 가게이므로 조회할 수 없습니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(),"서버 에러");



    private final int status;
    private final String message;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
}
