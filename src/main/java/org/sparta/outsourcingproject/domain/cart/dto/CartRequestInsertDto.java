package org.sparta.outsourcingproject.domain.cart.dto;

import lombok.Getter;

@Getter
public class CartRequestInsertDto {
    private CartInsertDto cart;
    private CartDetailInsertDto cartDetail;
}
