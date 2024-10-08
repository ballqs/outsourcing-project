package org.sparta.outsourcingproject.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.common.annotation.OrderLog;
import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.domain.cart.entity.Cart;
import org.sparta.outsourcingproject.domain.cart.service.CartDetailService;
import org.sparta.outsourcingproject.domain.order.dto.OrderDetailSelectDto;
import org.sparta.outsourcingproject.domain.order.dto.OrdersResponseSelectDto;
import org.sparta.outsourcingproject.domain.order.dto.OrdersSelectDto;
import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.sparta.outsourcingproject.domain.order.entity.OrdersProcessEnum;
import org.sparta.outsourcingproject.domain.order.exception.ForbiddenOrderStatusChangeException;
import org.sparta.outsourcingproject.domain.order.exception.ImmutableOrderStatusException;
import org.sparta.outsourcingproject.domain.order.repository.OrdersRepository;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.sparta.outsourcingproject.domain.user.service.UserCheckService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j(topic = "OrdersService")
@RequiredArgsConstructor
@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrderDetailService orderDetailService;
    private final CartDetailService cartDetailService;
    private final UserCheckService userCheckService;

    @Transactional
    public void orderComplete(Cart cart) {
        User user = userCheckService.findUser(cart.getUser().getId());
        Orders orders = Orders.CreateOrders(user , cart);
        Orders saveOrder = ordersRepository.save(orders);
        orderDetailService.orderComplete(saveOrder , cartDetailService.getAllCartDetails(cart.getId()));
    }

    @OrderLog
    @Transactional
    public void changeStatus(Long userId , Long orderId) {
        // 접근한 유저가 해당 주문을 받은 가게의 담당자와 일치하는지 검증!
        Orders orders = ordersRepository.findByIdOrThrow(orderId);
        if (!userId.equals(orders.getStore().getUser().getId())) {
            throw new ForbiddenOrderStatusChangeException(ErrorCode.FORBIDDEN_ORDER_STATUS_CHANGE);
        }

        // 주문 다음 상태로 이행 작업
        OrdersProcessEnum ordersProcessEnum = orders.getOrdersProcess();
        switch(ordersProcessEnum) {
            case ORDER:
                orders.updateOrdersProcess(OrdersProcessEnum.APPROVED);
                break;
            case APPROVED:
                orders.updateOrdersProcess(OrdersProcessEnum.IN_DELIVERY);
                break;
            case IN_DELIVERY:
                orders.updateOrdersProcess(OrdersProcessEnum.DELIVERED);
                break;
            case DELIVERED:
                throw new ImmutableOrderStatusException(ErrorCode.CONFLICT_DELIVERY_ALREADY_COMPLETED);
            default:
                throw new ImmutableOrderStatusException(ErrorCode.ORDER_CANCELLED);
        }
    }

    public OrdersResponseSelectDto getOrder(Long orderId) {
        Orders orders = ordersRepository.findByIdOrThrow(orderId);
        OrdersSelectDto ordersDto = new OrdersSelectDto(orders);
        List<OrderDetailSelectDto> orderDetailsDto =  orderDetailService.getOrderDetails(orders);
        return new OrdersResponseSelectDto(ordersDto , orderDetailsDto);
    }

    // TODO : 이 코드의 문제점! 주문이 엄청 많을 경우 상당히 비효율적임 추후 pageable를 적용할지 검토할 것
    public List<OrdersResponseSelectDto> getAllOrders(Long userId) {
        List<OrdersResponseSelectDto> ordersResponseSelectDtos = new ArrayList<>();
        List<Orders> orders = ordersRepository.findAllByUserId(userId);
        for (Orders order : orders) {
            OrdersSelectDto ordersDto = new OrdersSelectDto(order);
            List<OrderDetailSelectDto> orderDetailsDto =  orderDetailService.getOrderDetails(order);
            ordersResponseSelectDtos.add(new OrdersResponseSelectDto(ordersDto , orderDetailsDto));
        }
        return ordersResponseSelectDtos;
    }

    @Transactional
    public void cancel(Long userId , Long orderId) {
        Orders orders = ordersRepository.findByIdOrThrow(orderId);
        if (!userId.equals(orders.getStore().getUser().getId())) {
            throw new ForbiddenOrderStatusChangeException(ErrorCode.FORBIDDEN_NO_PERMISSION);
        }
        orders.updateOrdersProcess(OrdersProcessEnum.CANCEL);
    }

    public boolean deliveredCheck(Long orderId) {
        Orders orders = ordersRepository.findByIdOrThrow(orderId);
        return orders.getOrdersProcess().equals(OrdersProcessEnum.DELIVERED);
    }

    public Orders findOrders(Long orderId) {
        return ordersRepository.findByIdOrThrow(orderId);
    }
}
