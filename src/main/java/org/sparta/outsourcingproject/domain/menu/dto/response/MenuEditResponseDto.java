package org.sparta.outsourcingproject.domain.menu.dto.response;

import lombok.Getter;
import org.sparta.outsourcingproject.domain.menu.common.FoodType;
import org.sparta.outsourcingproject.domain.menu.entity.Menu;

@Getter
public class MenuEditResponseDto {
    private Long id;

    private String name;

    private FoodType type;

    private int price;

    private boolean soldOut;

    public MenuEditResponseDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.type = menu.getType();
        this.price = menu.getPrice();
        this.soldOut = menu.isSoldOut();
    }
}
