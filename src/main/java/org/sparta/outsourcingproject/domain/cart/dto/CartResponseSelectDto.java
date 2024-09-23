package org.sparta.outsourcingproject.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CartResponseSelectDto {
    private CartSelectDto cartSelectDto;
    private List<CartDetailSelectDto> cartDetailSelectDto;
}
