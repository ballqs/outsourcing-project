package org.sparta.outsourcingproject.domain.cart.dto;

import lombok.Getter;
import org.sparta.outsourcingproject.domain.cart.entity.CartDetail;

@Getter
public class CartDetailSelectDto {
    private Long id;
    private String menuName;
    private int menuPrice;
    private int cnt;

    public CartDetailSelectDto(CartDetail cartDetail) {
        this.id = cartDetail.getId();
        this.menuName = cartDetail.getMenuName();
        this.menuPrice = cartDetail.getMenuPrice();
        this.cnt = cartDetail.getCnt();
    }
}
