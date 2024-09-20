package org.sparta.outsourcingproject.domain.menu.dto;

import lombok.Getter;
import org.sparta.outsourcingproject.domain.menu.entity.Menu;

@Getter
public class MenuEditResponseDto {
    private Long id;

    private String field;

    private String type;

    private int price;

    private boolean soldOut;

    public MenuEditResponseDto(Long id, String field, String type, int price, boolean soldOut) {
        this.id = id;
        this.field = field;
        this.type = type;
        this.price = price;
        this.soldOut = soldOut;
    }

    public MenuEditResponseDto(Menu menu){
        this.id = menu.getId();
        this.field = menu.getField();
        this.type = menu.getType();
        this.price = menu.getPrice();
        this.soldOut = menu.isSoldOut();
    }
}
