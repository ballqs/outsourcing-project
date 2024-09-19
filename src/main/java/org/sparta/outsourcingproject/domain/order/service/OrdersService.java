package org.sparta.outsourcingproject.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.domain.order.repository.OrdersRepository;
import org.springframework.stereotype.Service;

@Slf4j(topic = "OrdersService")
@RequiredArgsConstructor
@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;

}
