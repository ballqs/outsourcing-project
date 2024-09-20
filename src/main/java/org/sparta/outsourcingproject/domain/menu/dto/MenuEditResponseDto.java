package org.sparta.outsourcingproject.domain.menu.dto;

import lombok.Getter;
import org.sparta.outsourcingproject.domain.menu.entity.Menu;

@Getter
public class MenuEditResponseDto {
    private Long id;

    private String name;

    private String type;

    private int price;

    private boolean soldOut;

    public MenuEditResponseDto(Long id, String name, String type, int price, boolean soldOut) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.soldOut = soldOut;
    }

    public MenuEditResponseDto(Menu menu){
        this.id = menu.getId();
        this.name = menu.getName();
        this.type = menu.getType();
        this.price = menu.getPrice();
        this.soldOut = menu.isSoldOut();
    }
}
