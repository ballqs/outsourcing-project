package org.sparta.outsourcingproject.domain.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sparta.outsourcingproject.common.config.JwtFilter;
import org.sparta.outsourcingproject.common.config.JwtUtil;
import org.sparta.outsourcingproject.common.data.TestData;
import org.sparta.outsourcingproject.common.dto.AuthUser;
import org.sparta.outsourcingproject.domain.order.dto.OrderDetailSelectDto;
import org.sparta.outsourcingproject.domain.order.dto.OrdersResponseSelectDto;
import org.sparta.outsourcingproject.domain.order.dto.OrdersSelectDto;
import org.sparta.outsourcingproject.domain.order.entity.OrderDetail;
import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.sparta.outsourcingproject.domain.order.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {OrderController.class})
class OrderControllerTest {

    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean // 깡통 객체를 주입받는다. (있는 척 한다)
    private OrdersService ordersService;

    @SpyBean
    private JwtUtil jwtUtil; // JwtUtil 모킹

    @Autowired
    private OrderController orderController;

    private String token;
    private AuthUser authUser;

    @BeforeEach
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(orderController)
                .addFilters(new JwtFilter(jwtUtil)).build();

        TestData.initToken(jwtUtil);
        this.authUser = TestData.AUTH_USER;
        this.token = TestData.TOKEN;
    }

    @Test
    public void 주문_상태_변경_동작_완료() throws Exception {
        // given
        long orderId = 1L;

        doNothing().when(ordersService).changeStatus(any(), anyLong());

        // when
        ResultActions resultActions = mvc.perform(post("/api/orders/change-status/{orderId}" , orderId)
                .header(HttpHeaders.AUTHORIZATION , token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void 주문_단건_조회_동작_완료() throws Exception {
        // given
        long orderId = 1L;

        Orders orders = new Orders();
        OrderDetail orderDetail = new OrderDetail();
        OrdersSelectDto ordersSelectDto = new OrdersSelectDto(orders);
        OrderDetailSelectDto orderDetailSelectDto = new OrderDetailSelectDto(orderDetail);
        List<OrderDetailSelectDto> orderDetailSelectDtos = new ArrayList<>();
        orderDetailSelectDtos.add(orderDetailSelectDto);
        OrdersResponseSelectDto ordersResponseSelectDto = new OrdersResponseSelectDto(ordersSelectDto , orderDetailSelectDtos);

        given(ordersService.getOrder(anyLong())).willReturn(ordersResponseSelectDto);

        // when
        ResultActions resultActions = mvc.perform(get("/api/orders/{orderId}" , orderId)
                .header(HttpHeaders.AUTHORIZATION , token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void 주문_다건_조회_동작_완료() throws Exception {
        // given
        Orders orders = new Orders();
        OrderDetail orderDetail = new OrderDetail();
        OrdersSelectDto ordersSelectDto = new OrdersSelectDto(orders);
        OrderDetailSelectDto orderDetailSelectDto = new OrderDetailSelectDto(orderDetail);
        List<OrderDetailSelectDto> orderDetailSelectDtos = new ArrayList<>();
        orderDetailSelectDtos.add(orderDetailSelectDto);
        OrdersResponseSelectDto ordersResponseSelectDto = new OrdersResponseSelectDto(ordersSelectDto , orderDetailSelectDtos);
        List<OrdersResponseSelectDto> ordersResponseSelectDtos = new ArrayList<>();
        ordersResponseSelectDtos.add(ordersResponseSelectDto);

        given(ordersService.getAllOrders(anyLong())).willReturn(ordersResponseSelectDtos);

        // when
        ResultActions resultActions = mvc.perform(get("/api/orders")
                .header(HttpHeaders.AUTHORIZATION , token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk()).andDo(print());
    }

}