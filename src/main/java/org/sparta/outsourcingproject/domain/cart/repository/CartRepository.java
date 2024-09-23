package org.sparta.outsourcingproject.domain.cart.repository;

import org.sparta.outsourcingproject.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);
    Optional<Cart> findByUserIdAndStoreId(Long userId, Long storeId);
    void deleteByUserId(Long userId);
    int countByUserId(Long userId);
    boolean existsByUserIdAndStoreIdNot(Long userId, Long storeId);
}
