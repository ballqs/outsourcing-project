package org.sparta.outsourcingproject.domain.cart.java;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sparta.outsourcingproject.domain.cart.entity.CartDetail;

@AllArgsConstructor
@Getter
public class CartDetailEvent {
    private CartDetail cartDetail;
}
