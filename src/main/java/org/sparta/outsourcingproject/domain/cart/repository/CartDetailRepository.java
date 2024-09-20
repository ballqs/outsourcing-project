package org.sparta.outsourcingproject.domain.cart.repository;

import org.sparta.outsourcingproject.domain.cart.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    @Query("SELECT SUM(c.menuPrice * c.cnt) FROM CartDetail c where c.cart.id = :cardId")
    int getSumAmt(Long cardId);
    List<CartDetail> findAllByCartId(Long cartId);
    int countByCartId(Long cartId);

    default CartDetail findByIdOrThrow(Long cartDetailId) {
        return findById(cartDetailId).orElseThrow(() -> new IllegalArgumentException("장바구니 상세 정보가 없습니다."));
    }
}
