package org.sparta.outsourcingproject.domain.cart.service;

import lombok.RequiredArgsConstructor;
import org.sparta.outsourcingproject.domain.cart.entity.CartDetail;
import org.sparta.outsourcingproject.domain.cart.repository.CartDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartDetailService {

    private final CartDetailRepository cartDetailRepository;

    public CartDetail getCartDetail(Long cartDetailId) {
        return cartDetailRepository.findByIdOrElseThrow(cartDetailId);
    }

    public int getSumAmt(Long cartId) {
        return cartDetailRepository.getSumAmt(cartId);
    }

    public void saveCartDetail(CartDetail cartDetail) {
        cartDetailRepository.save(cartDetail);
    }

    public void deleteCartDetail(Long cartDetailId) {
        cartDetailRepository.deleteById(cartDetailId);
    }

    public List<CartDetail> getAllCartDetails(Long cartId) {
        return cartDetailRepository.findAllByCartId(cartId);
    }
}
