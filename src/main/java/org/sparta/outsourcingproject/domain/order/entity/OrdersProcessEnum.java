package org.sparta.outsourcingproject.domain.order.entity;

public enum OrdersProcessEnum {
    ORDER("ORDER"),         // 주문 후
    APPROVED("APPROVED"),       // 승인
    IN_DELIVERY("IN_DELIVERY"), // 배달 중
    DELIVERED("DELIVERED");     // 배달 완료

    private String status;

    OrdersProcessEnum(String status) {
        this.status = status;
    }
}
