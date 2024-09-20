package org.sparta.outsourcingproject.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.sparta.outsourcingproject.common.annotation.Auth;
import org.sparta.outsourcingproject.common.dto.AuthUser;
import org.sparta.outsourcingproject.common.dto.ResponseDto;
import org.sparta.outsourcingproject.domain.order.dto.OrdersResponseSelectDto;
import org.sparta.outsourcingproject.domain.order.service.OrdersService;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrdersService ordersService;

    @PostMapping("/change-status/{orderId}")
    public ResponseEntity<ResponseDto<String>> changeStatus(@Auth AuthUser authUser , @PathVariable Long orderId) {
        ordersService.changeStatus(authUser.getUserId() , orderId);
        return ResponseEntity.ok(new ResponseDto<>(200 , null , "주문 상태를 변경했습니다."));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseDto<OrdersResponseSelectDto>> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(new ResponseDto<>(200 , ordersService.getOrder(orderId) , "주문을 조회했습니다."));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<OrdersResponseSelectDto>>> getAllOrders(@Auth AuthUser authUser) {
        return ResponseEntity.ok(new ResponseDto<>(200 , ordersService.getAllOrders(authUser.getUserId()) , "모든 주문 목록을 조회했습니다."));
    }
}
