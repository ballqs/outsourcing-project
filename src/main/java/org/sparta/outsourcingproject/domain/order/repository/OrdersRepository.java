package org.sparta.outsourcingproject.domain.order.repository;

import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
