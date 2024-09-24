package org.sparta.outsourcingproject.domain.order.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sparta.outsourcingproject.domain.cart.entity.Cart;
import org.sparta.outsourcingproject.domain.cart.service.CartDetailService;
import org.sparta.outsourcingproject.domain.order.dto.OrderDetailSelectDto;
import org.sparta.outsourcingproject.domain.order.dto.OrdersResponseSelectDto;
import org.sparta.outsourcingproject.domain.order.entity.OrderDetail;
import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.sparta.outsourcingproject.domain.order.repository.OrdersRepository;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreCreateRequestDto;
import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.sparta.outsourcingproject.domain.store.enums.StoreOperationStatus;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.sparta.outsourcingproject.domain.user.service.UserService;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrdersRepository ordersRepository;
    @Mock
    private OrderDetailService orderDetailService;
    @Mock
    private CartDetailService cartDetailService;
    @Mock
    private UserService userService;
    @InjectMocks
    private OrdersService ordersService;

    @Test
    public void orderComplete_동작_완료() {
        // given
        LocalTime openTime = LocalTime.now();
        LocalTime closeTime = LocalTime.now().plusMinutes(8);

        Long userId = 1L;
        User user = new User();
        ReflectionTestUtils.setField(user , "id" , userId);

        StoreCreateRequestDto requestDto = new StoreCreateRequestDto("이름" , "카테고리" , "010-1234-1234" , openTime , closeTime , 15000 , "주소" , StoreOperationStatus.OPERATE , BigDecimal.valueOf(0));
        Store store = Store.createStore(requestDto , user);

        Long cartId = 1L;
        Cart cart = new Cart(user , store , 0);
        ReflectionTestUtils.setField(cart , "id" , cartId);

        Orders orders = new Orders();

        given(userService.findUser(anyLong())).willReturn(user);
        given(ordersRepository.save(any())).willReturn(orders);
        doNothing().when(orderDetailService).orderComplete(any() , any());

        // when
        ordersService.orderComplete(cart);

        // then
        verify(orderDetailService , times(1)).orderComplete(any() , any());
    }

    @Test
    public void changeStatus_동작_완료() {
        // given
        Long userId = 1L;
        Long orderId = 1L;
        Long cartId = 1L;

        User user = new User();
        ReflectionTestUtils.setField(user , "id" , userId);

        LocalTime openTime = LocalTime.now();
        LocalTime closeTime = LocalTime.now().plusMinutes(8);

        StoreCreateRequestDto requestDto = new StoreCreateRequestDto("이름" , "카테고리" , "010-1234-1234" , openTime , closeTime , 15000 , "주소" , StoreOperationStatus.OPERATE , BigDecimal.valueOf(0));
        Store store = Store.createStore(requestDto , user);

        Cart cart = new Cart(user , store , 15000);
        ReflectionTestUtils.setField(cart , "id" , cartId);

        Orders orders = Orders.CreateOrders(user , cart);
        ReflectionTestUtils.setField(orders , "id" , orderId);

        given(ordersRepository.findByIdOrThrow(anyLong())).willReturn(orders);

        // when
        ordersService.changeStatus(userId , orderId);

        // then

    }

    @Test
    public void getOrder_동작_완료() {
        // given
        Long userId = 1L;
        Long orderId = 1L;
        Long cartId = 1L;
        Long orderDetailId = 1L;

        User user = new User();
        ReflectionTestUtils.setField(user , "id" , userId);

        LocalTime openTime = LocalTime.now();
        LocalTime closeTime = LocalTime.now().plusMinutes(8);

        StoreCreateRequestDto requestDto = new StoreCreateRequestDto("이름" , "카테고리" , "010-1234-1234" , openTime , closeTime , 15000 , "주소" , StoreOperationStatus.OPERATE , BigDecimal.valueOf(0));
        Store store = Store.createStore(requestDto , user);

        Cart cart = new Cart(user , store , 15000);
        ReflectionTestUtils.setField(cart , "id" , cartId);

        Orders orders = Orders.CreateOrders(user , cart);
        ReflectionTestUtils.setField(orders , "id" , orderId);

        OrderDetail orderDetail = new OrderDetail();
        ReflectionTestUtils.setField(orderDetail , "id" , orderDetailId);

        List<OrderDetailSelectDto> orderDetailsDto = List.of(
                new OrderDetailSelectDto(orderDetail)
        );

        given(ordersRepository.findByIdOrThrow(anyLong())).willReturn(orders);
        given(orderDetailService.getOrderDetails(any())).willReturn(orderDetailsDto);

        // when
        OrdersResponseSelectDto ordersResponseSelectDto = ordersService.getOrder(orderId);

        // then
        assertThat(ordersResponseSelectDto.getOrders().getId()).isEqualTo(orderId);
    }
}
