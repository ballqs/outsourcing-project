package org.sparta.outsourcingproject.domain.cart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.common.annotation.Auth;
import org.sparta.outsourcingproject.common.dto.AuthUser;
import org.sparta.outsourcingproject.common.dto.ResponseDto;
import org.sparta.outsourcingproject.domain.cart.dto.CartDetailUpdateDto;
import org.sparta.outsourcingproject.domain.cart.dto.CartRequestInsertDto;
import org.sparta.outsourcingproject.domain.cart.dto.CartResponseSelectDto;
import org.sparta.outsourcingproject.domain.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<ResponseDto<String>> createCart(@Auth AuthUser authUser , @Valid @RequestBody CartRequestInsertDto cartRequestInsertDto) {
        cartService.createCart(cartRequestInsertDto);
        return ResponseEntity.ok(new ResponseDto<>(200 , null , "장바구니에 등록되었습니다."));
    }

    @PatchMapping("/{cartDetailId}")
    public ResponseEntity<ResponseDto<String>> updateCart(@Auth AuthUser authUser , @PathVariable Long cartDetailId , @Valid @RequestBody CartDetailUpdateDto cartDetailUpdateDto) {
        cartService.updateCart(cartDetailId , cartDetailUpdateDto);
        return ResponseEntity.ok(new ResponseDto<>(200 , null , "장바구니에 수정되었습니다."));
    }

    @DeleteMapping("/{cartDetailId}")
    public ResponseEntity<ResponseDto<String>> deleteCart(@PathVariable Long cartDetailId) {
        cartService.deleteCart(cartDetailId);
        return ResponseEntity.ok(new ResponseDto<>(200 , null , "장바구니에 삭제되었습니다."));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<CartResponseSelectDto>> getCarts(@Auth AuthUser authUser) {
        return ResponseEntity.ok(new ResponseDto<>(200 , cartService.getCarts(authUser.getUserId()) , "조회가 완료되었습니다."));
    }

    @PostMapping("/order-complete")
    public ResponseEntity<ResponseDto<String>> orderComplete(@Auth AuthUser authUser) {
        cartService.orderComplete(authUser.getUserId());
        return ResponseEntity.ok(new ResponseDto<>(200 , null , "주문 완료되었습니다."));
    }

}
