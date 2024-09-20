package org.sparta.outsourcingproject.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.common.annotation.OrderLog;
import org.sparta.outsourcingproject.domain.cart.entity.Cart;
import org.sparta.outsourcingproject.domain.cart.service.CartDetailService;
import org.sparta.outsourcingproject.domain.order.dto.OrderDetailSelectDto;
import org.sparta.outsourcingproject.domain.order.dto.OrdersResponseSelectDto;
import org.sparta.outsourcingproject.domain.order.dto.OrdersSelectDto;
import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.sparta.outsourcingproject.domain.order.entity.OrdersProcessEnum;
import org.sparta.outsourcingproject.domain.order.repository.OrdersRepository;
import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j(topic = "OrdersService")
@RequiredArgsConstructor
@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrderDetailService orderDetailService;
    private final CartDetailService cartDetailService;

    public void orderComplete(Cart cart) {
        // user 정보
//        Long userId = cart.getUserId();
        // User user = userService.findById(userId);
        User user = new User();

        // ORDER 로 default 설정
        Orders orders = new Orders();

        Orders saveOrder = ordersRepository.save(orders);
        orderDetailService.orderComplete(saveOrder , cartDetailService.getAllCartDetails(cart.getId()));
    }

    @OrderLog
    public void changeStatus(Long userId , Long orderId) {
        // 접근한 유저가 해당 주문을 받은 가게의 담당자와 일치하는지 검증!
        Orders orders = ordersRepository.findByIdOrThrow(orderId);
//        if (userId.equals(orders.user.getUserId())) {
//            throw new IllegalArgumentException("니 주문 아님");
//        }

        // 주문 다음 상태로 이행 작업
        OrdersProcessEnum ordersProcessEnum = orders.getOrdersProcess();
        switch(ordersProcessEnum) {
            case ORDER:
                // 최소 주문 금액을 넘었는지?
//                Store store = new Store();
                int minPrice = 15000;
                if (orders.getTotalAmt() < minPrice) throw new IllegalArgumentException("최소 주문 금액을 넘기지 못한 주문은 승인 불가입니다.");
                break;
            case APPROVED:
                // 단순 배달로 이행할지?
                break;
            case IN_DELIVERY:
                // 단순 배달 완료 처리 할지?
                break;
            default:
                throw new IllegalArgumentException("이미 배달완료된 건입니다.");
        }
        ordersProcessEnum = OrdersProcessEnum.values()[ordersProcessEnum.getSeq()];

        orders.updateOrdersProcess(ordersProcessEnum);
        ordersRepository.save(orders);
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
}
