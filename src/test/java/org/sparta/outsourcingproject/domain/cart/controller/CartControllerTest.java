package org.sparta.outsourcingproject.domain.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sparta.outsourcingproject.common.config.JwtFilter;
import org.sparta.outsourcingproject.common.config.JwtUtil;
import org.sparta.outsourcingproject.common.data.TestData;
import org.sparta.outsourcingproject.common.dto.AuthUser;
import org.sparta.outsourcingproject.domain.cart.dto.*;
import org.sparta.outsourcingproject.domain.cart.entity.Cart;
import org.sparta.outsourcingproject.domain.cart.entity.CartDetail;
import org.sparta.outsourcingproject.domain.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = {CartController.class})
class CartControllerTest {

    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean // 깡통 객체를 주입받는다. (있는 척 한다)
    private CartService cartService;

    @SpyBean
    private JwtUtil jwtUtil; // JwtUtil 모킹

    @Autowired
    private CartController cartController;

    private String token;
    private AuthUser authUser;

    @BeforeEach
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(cartController)
                .addFilters(new JwtFilter(jwtUtil)).build();

        TestData.initToken(jwtUtil);
        this.authUser = TestData.AUTH_USER;
        this.token = TestData.TOKEN;
    }

    @Test
    public void 장바구니_등록_완료() throws Exception {
        // given
        long storeId = 1L;
        long menuId = 1L;
        int cnt = 1;

        CartInsertDto cart = new CartInsertDto();
        ReflectionTestUtils.setField(cart , "storeId" , storeId);
        CartDetailInsertDto cartDetail = new CartDetailInsertDto();
        ReflectionTestUtils.setField(cartDetail , "menuId" , menuId);
        ReflectionTestUtils.setField(cartDetail , "cnt" , cnt);

        CartRequestInsertDto requestDto = new CartRequestInsertDto();
        ReflectionTestUtils.setField(requestDto , "cart" , cart);
        ReflectionTestUtils.setField(requestDto , "cartDetail" , cartDetail);

        String postInfo = objectMapper.writeValueAsString(requestDto);

        doNothing().when(cartService).createCart(anyLong(), any());

        // when
        ResultActions resultActions = mvc.perform(post("/api/cart")
                .header(HttpHeaders.AUTHORIZATION , token)
                .content(postInfo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void 장바구니_등록_실패_가게_번호_없음() throws Exception {
        // given
        long menuId = 1L;
        int cnt = 1;

        CartInsertDto cart = new CartInsertDto();
        ReflectionTestUtils.setField(cart , "storeId" , null);
        CartDetailInsertDto cartDetail = new CartDetailInsertDto();
        ReflectionTestUtils.setField(cartDetail , "menuId" , menuId);
        ReflectionTestUtils.setField(cartDetail , "cnt" , cnt);

        CartRequestInsertDto requestDto = new CartRequestInsertDto();
        ReflectionTestUtils.setField(requestDto , "cart" , cart);
        ReflectionTestUtils.setField(requestDto , "cartDetail" , cartDetail);

        String postInfo = objectMapper.writeValueAsString(requestDto);

        doNothing().when(cartService).createCart(anyLong(), any());

        // when
        ResultActions resultActions = mvc.perform(post("/api/cart")
                .header(HttpHeaders.AUTHORIZATION , token)
                .content(postInfo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    public void 장바구니_등록_실패_메뉴_번호_없음() throws Exception {
        // given
        long storeId = 1L;
        int cnt = 1;

        CartInsertDto cart = new CartInsertDto();
        ReflectionTestUtils.setField(cart , "storeId" , storeId);
        CartDetailInsertDto cartDetail = new CartDetailInsertDto();
        ReflectionTestUtils.setField(cartDetail , "menuId" , null);
        ReflectionTestUtils.setField(cartDetail , "cnt" , cnt);

        CartRequestInsertDto requestDto = new CartRequestInsertDto();
        ReflectionTestUtils.setField(requestDto , "cart" , cart);
        ReflectionTestUtils.setField(requestDto , "cartDetail" , cartDetail);

        String postInfo = objectMapper.writeValueAsString(requestDto);

        doNothing().when(cartService).createCart(anyLong(), any());

        // when
        ResultActions resultActions = mvc.perform(post("/api/cart")
                .header(HttpHeaders.AUTHORIZATION , token)
                .content(postInfo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    public void 장바구니_등록_실패_메뉴_개수_없음() throws Exception {
        // given
        long storeId = 1L;
        long menuId = 1L;

        CartInsertDto cart = new CartInsertDto();
        ReflectionTestUtils.setField(cart , "storeId" , storeId);
        CartDetailInsertDto cartDetail = new CartDetailInsertDto();
        ReflectionTestUtils.setField(cartDetail , "menuId" , menuId);
        ReflectionTestUtils.setField(cartDetail , "cnt" , 0);

        CartRequestInsertDto requestDto = new CartRequestInsertDto();
        ReflectionTestUtils.setField(requestDto , "cart" , cart);
        ReflectionTestUtils.setField(requestDto , "cartDetail" , cartDetail);

        String postInfo = objectMapper.writeValueAsString(requestDto);

        doNothing().when(cartService).createCart(anyLong(), any());

        // when
        ResultActions resultActions = mvc.perform(post("/api/cart")
                .header(HttpHeaders.AUTHORIZATION , token)
                .content(postInfo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    public void 장바구니_수정_완료() throws Exception {
        // given
        long menuId = 1L;
        int cnt = 1;

        long cartDetailId = 1L;

        CartDetailUpdateDto requestDto = new CartDetailUpdateDto();
        ReflectionTestUtils.setField(requestDto , "menuId" , menuId);
        ReflectionTestUtils.setField(requestDto , "cnt" , cnt);

        String postInfo = objectMapper.writeValueAsString(requestDto);

        doNothing().when(cartService).updateCart(anyLong(), anyLong() , any());

        // when
        ResultActions resultActions = mvc.perform(patch("/api/cart/{cartDetailId}" , cartDetailId)
                .header(HttpHeaders.AUTHORIZATION , token)
                .content(postInfo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void 장바구니_수정_실패_메뉴_번호_없음() throws Exception {
        // given
        int cnt = 1;

        long cartDetailId = 1L;

        CartDetailUpdateDto requestDto = new CartDetailUpdateDto();
        ReflectionTestUtils.setField(requestDto , "menuId" , null);
        ReflectionTestUtils.setField(requestDto , "cnt" , cnt);

        String postInfo = objectMapper.writeValueAsString(requestDto);

        doNothing().when(cartService).updateCart(anyLong(), anyLong() , any());

        // when
        ResultActions resultActions = mvc.perform(patch("/api/cart/{cartDetailId}" , cartDetailId)
                .header(HttpHeaders.AUTHORIZATION , token)
                .content(postInfo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    public void 장바구니_수정_실패_메뉴_개수_없음() throws Exception {
        // given
        long menuId = 1L;

        long cartDetailId = 1L;

        CartDetailUpdateDto requestDto = new CartDetailUpdateDto();
        ReflectionTestUtils.setField(requestDto , "menuId" , menuId);
        ReflectionTestUtils.setField(requestDto , "cnt" , 0);

        String postInfo = objectMapper.writeValueAsString(requestDto);

        doNothing().when(cartService).updateCart(anyLong(), anyLong() , any());

        // when
        ResultActions resultActions = mvc.perform(patch("/api/cart/{cartDetailId}" , cartDetailId)
                .header(HttpHeaders.AUTHORIZATION , token)
                .content(postInfo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    public void 장바구니_삭제_완료() throws Exception {
        // given
        long cartDetailId = 1L;

        doNothing().when(cartService).deleteCart(anyLong(), anyLong());

        // when
        ResultActions resultActions = mvc.perform(delete("/api/cart/{cartDetailId}" , cartDetailId)
                .header(HttpHeaders.AUTHORIZATION , token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void 장바구니_단건_조회_완료() throws Exception {
        // given
        Cart cart = new Cart();
        CartSelectDto cartSelectDto = new CartSelectDto(cart);
        
        CartDetail cartDetail = new CartDetail();
        CartDetailSelectDto cartDetailSelectDto = new CartDetailSelectDto(cartDetail);
        List<CartDetailSelectDto> cartDetailSelectDtos = List.of(
                cartDetailSelectDto
        );

        CartResponseSelectDto cartResponseSelectDto = new CartResponseSelectDto(cartSelectDto , cartDetailSelectDtos);
        given(cartService.getCarts(anyLong())).willReturn(cartResponseSelectDto);

        // when
        ResultActions resultActions = mvc.perform(get("/api/cart")
                .header(HttpHeaders.AUTHORIZATION , token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk()).andDo(print());
    }



    @Test
    public void 장바구니_주문_완료() throws Exception {
        // given
        doNothing().when(cartService).orderComplete(anyLong());

        // when
        ResultActions resultActions = mvc.perform(post("/api/cart/order-complete")
                .header(HttpHeaders.AUTHORIZATION , token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk()).andDo(print());
    }

}