package org.sparta.outsourcingproject.domain.order.repository;

import org.sparta.outsourcingproject.domain.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
