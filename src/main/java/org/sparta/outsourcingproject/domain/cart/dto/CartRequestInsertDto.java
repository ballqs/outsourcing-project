package org.sparta.outsourcingproject.domain.cart.dto;

import jakarta.validation.Valid;
import lombok.Getter;

@Getter
public class CartRequestInsertDto {
    @Valid
    private CartInsertDto cart;
    @Valid
    private CartDetailInsertDto cartDetail;
}
