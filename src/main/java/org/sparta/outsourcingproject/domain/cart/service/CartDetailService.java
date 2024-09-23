package org.sparta.outsourcingproject.domain.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.domain.cart.entity.CartDetail;
import org.sparta.outsourcingproject.domain.cart.java.CartDetailEvent;
import org.sparta.outsourcingproject.domain.cart.repository.CartDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Slf4j(topic = "CartDetailService")
@RequiredArgsConstructor
@Service
public class CartDetailService {

    private final CartDetailRepository cartDetailRepository;

    public CartDetail getCartDetail(Long cartDetailId) {
        return cartDetailRepository.findByIdOrThrow(cartDetailId);
    }

    public int getSumAmt(Long cartId) {
        return cartDetailRepository.getSumAmt(cartId);
    }

//    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveCartDetail(CartDetail cartDetail) {
        cartDetailRepository.save(cartDetail);
//        cartDetailRepository.save(event.getCartDetail());
    }

    public void deleteCartDetail(Long cartDetailId) {
        cartDetailRepository.deleteById(cartDetailId);
    }

    public List<CartDetail> getAllCartDetails(Long cartId) {
        return cartDetailRepository.findAllByCartId(cartId);
    }

    public int countCartDetail(Long cartId) {
        return cartDetailRepository.countByCartId(cartId);
    }
}
