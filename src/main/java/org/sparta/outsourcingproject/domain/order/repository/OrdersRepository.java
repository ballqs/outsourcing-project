package org.sparta.outsourcingproject.domain.order.repository;

import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.NotFoundException;
import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findAllByUserId(Long userId);

    default Orders findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.ORDER_NOT_FOUND));
    }
}
