package org.sparta.outsourcingproject.domain.order.repository;

import org.sparta.outsourcingproject.domain.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findAllByOrdersId(Long orderId);
}
