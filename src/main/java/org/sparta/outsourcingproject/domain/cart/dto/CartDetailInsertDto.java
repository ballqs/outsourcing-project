package org.sparta.outsourcingproject.domain.cart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CartDetailInsertDto {
    @NotNull
    private Long menuId;

    @NotBlank
    private String menuName;

    @NotNull
    private int menuPrice;

    @NotNull
    private int cnt;
}
