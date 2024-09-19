package org.sparta.outsourcingproject.domain.cart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.common.dto.ResponseDto;
import org.sparta.outsourcingproject.domain.cart.dto.CartDetailUpdateDto;
import org.sparta.outsourcingproject.domain.cart.dto.CartRequestInsertDto;
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
    public ResponseEntity<ResponseDto<String>> createCart(@Valid @RequestBody CartRequestInsertDto cartRequestInsertDto) {
        cartService.createCart(cartRequestInsertDto);
        return ResponseEntity.ok(new ResponseDto<>(200 , null , "장바구니에 등록되었습니다."));
    }

    @PatchMapping("/{cartDetailId}")
    public ResponseEntity<ResponseDto<String>> updateCart(@PathVariable Long cartDetailId , @Valid @RequestBody CartDetailUpdateDto cartDetailUpdateDto) {
        cartService.updateCart(cartDetailId , cartDetailUpdateDto);
        return ResponseEntity.ok(new ResponseDto<>(200 , null , "장바구니에 수정되었습니다."));
    }

    @DeleteMapping("/{cartDetailId}")
    public ResponseEntity<ResponseDto<String>> deleteCart(@PathVariable Long cartDetailId) {
        cartService.deleteCart(cartDetailId);
        return ResponseEntity.ok(new ResponseDto<>(200 , null , "장바구니에 삭제되었습니다."));
    }

}
