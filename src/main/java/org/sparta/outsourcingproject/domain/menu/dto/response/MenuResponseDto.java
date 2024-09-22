package org.sparta.outsourcingproject.domain.menu.dto.response;

import lombok.Getter;
import org.sparta.outsourcingproject.domain.menu.common.FoodType;
import org.sparta.outsourcingproject.domain.menu.entity.Menu;

@Getter
public class MenuResponseDto {
    private Long id;

    private String name;

    private FoodType type;

    private int price;

    public MenuResponseDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.type = menu.getType();
        this.price = menu.getPrice();
    }
}
