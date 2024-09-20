package org.sparta.outsourcingproject.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrdersResponseSelectDto {
    private OrdersSelectDto orders;
    private List<OrderDetailSelectDto> orderDetails;
}
