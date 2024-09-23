package org.sparta.outsourcingproject.domain.cart.dto;

import lombok.Getter;
import org.sparta.outsourcingproject.domain.cart.entity.Cart;

@Getter
public class CartSelectDto {
    private Long id;
    private int totalAmt;

    public CartSelectDto(Cart cart) {
        this.id = cart.getId();
        this.totalAmt = cart.getTotalAmt();
    }
}
