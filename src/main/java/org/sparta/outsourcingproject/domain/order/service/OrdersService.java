package org.sparta.outsourcingproject.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.domain.cart.entity.Cart;
import org.sparta.outsourcingproject.domain.cart.service.CartDetailService;
import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.sparta.outsourcingproject.domain.order.repository.OrdersRepository;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.springframework.stereotype.Service;

@Slf4j(topic = "OrdersService")
@RequiredArgsConstructor
@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrderDetailService orderDetailService;
    private final CartDetailService cartDetailService;

    public void orderComplete(Cart cart) {
        // user 정보
        Long userId = cart.getUserId();
        // User user = userService.findById(userId);
        User user = new User();

        Orders orders = new Orders();

        Orders saveOrder = ordersRepository.save(orders);
        orderDetailService.orderComplete(saveOrder , cartDetailService.getAllCartDetails(cart.getId()));
    }
}
