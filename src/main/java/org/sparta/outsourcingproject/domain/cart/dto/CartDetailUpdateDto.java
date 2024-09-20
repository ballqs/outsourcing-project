package org.sparta.outsourcingproject.domain.cart.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CartDetailUpdateDto {
    @NotNull
    private Long menuId;

    @NotNull
    private int cnt;
}
