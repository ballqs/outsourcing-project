package org.sparta.outsourcingproject.domain.order.entity;

import lombok.Getter;

@Getter
public enum OrdersProcessEnum {
    ORDER(1,"ORDER"),               // 주문 전
    APPROVED(2,"APPROVED"),         // 승인(요리시작)
    IN_DELIVERY(3,"IN_DELIVERY"),   // 배달 중
    DELIVERED(4,"DELIVERED");       // 배달 완료

    private String status;
    private int seq;

    OrdersProcessEnum(int seq , String status) {
        this.seq = seq;
        this.status = status;
    }
}
