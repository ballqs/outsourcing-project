package org.sparta.outsourcingproject.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.domain.cart.entity.CartDetail;
import org.sparta.outsourcingproject.domain.order.dto.OrderDetailSelectDto;
import org.sparta.outsourcingproject.domain.order.entity.OrderDetail;
import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.sparta.outsourcingproject.domain.order.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public void orderComplete(Orders orders, List<CartDetail> cartDetails) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartDetail cartDetail : cartDetails) {
            orderDetails.add(new OrderDetail(orders, cartDetail));
        }
        orderDetailRepository.saveAll(orderDetails);
    }

    public List<OrderDetailSelectDto> getOrderDetails(Orders orders) {
        List<OrderDetail> orderDetails = orders.getOrderDetails();
        return orderDetails.stream().map(OrderDetailSelectDto::new).toList();
    }
}
