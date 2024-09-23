package org.sparta.outsourcingproject.domain.cart.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class CartDetailUpdateDto {
    @NotNull
    private Long menuId;

    @Positive
    private int cnt;

    // 추가
    @NotNull
    private Long storeId;

}
