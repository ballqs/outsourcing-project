package org.sparta.outsourcingproject.domain.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.sparta.outsourcingproject.common.config.JwtFilter;
import org.sparta.outsourcingproject.common.config.JwtUtil;
import org.sparta.outsourcingproject.common.dto.AuthUser;
import org.sparta.outsourcingproject.domain.order.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

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

//        this.authUser = new AuthUser(1L , "test@test.com");
//        this.token = TestCommonData.TOKEN;
    }

}