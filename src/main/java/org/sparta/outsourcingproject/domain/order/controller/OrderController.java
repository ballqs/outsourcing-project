package org.sparta.outsourcingproject.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.sparta.outsourcingproject.common.dto.ResponseDto;
import org.sparta.outsourcingproject.domain.order.service.OrdersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrdersService ordersService;

    @PostMapping("/change-status/{orderId}")
    public ResponseEntity<ResponseDto<String>> changeStatus(@PathVariable Long orderId) {
        return ResponseEntity.ok(new ResponseDto<>(200 , null , "주문이 정상적으로 XX되었습니다."));
    }
}
