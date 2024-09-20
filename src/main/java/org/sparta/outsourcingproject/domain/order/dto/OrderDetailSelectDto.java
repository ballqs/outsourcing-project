package org.sparta.outsourcingproject.domain.order.dto;

import lombok.Getter;
import org.sparta.outsourcingproject.domain.order.entity.OrderDetail;

@Getter
public class OrderDetailSelectDto {
    private Long id;
    private String menuName;
    private int menuPrice;
    private int cnt;

    public OrderDetailSelectDto(OrderDetail orderDetail) {
        this.id = orderDetail.getId();
        this.menuName = orderDetail.getMenuName();
        this.menuPrice = orderDetail.getMenuPrice();
        this.cnt = orderDetail.getCnt();
    }
}
