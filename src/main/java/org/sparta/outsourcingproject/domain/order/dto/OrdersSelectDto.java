package org.sparta.outsourcingproject.domain.order.dto;

import lombok.Getter;
import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.sparta.outsourcingproject.domain.order.entity.OrdersProcessEnum;

@Getter
public class OrdersSelectDto {
    private Long id;
    private String userTel;
    private String zip;
    private String address;
    private String addressDetail;
    private OrdersProcessEnum ordersProcess;
    private int totalAmt;

    public OrdersSelectDto(Orders orders) {
        this.id = orders.getId();
        this.userTel = orders.getUserTel();
        this.zip = orders.getZip();
        this.address = orders.getAddress();
        this.addressDetail = orders.getAddressDetail();
        this.ordersProcess = orders.getOrdersProcess();
        this.totalAmt = orders.getTotalAmt();
    }
}
