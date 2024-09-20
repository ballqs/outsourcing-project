package org.sparta.outsourcingproject.domain.order.repository;

import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findAllByUserId(Long userId);

    default Orders findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다."));
    }
}
