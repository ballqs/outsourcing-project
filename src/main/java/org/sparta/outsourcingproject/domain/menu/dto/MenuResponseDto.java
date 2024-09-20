package org.sparta.outsourcingproject.domain.menu.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.sparta.outsourcingproject.domain.menu.entity.Menu;

@Getter
public class MenuResponseDto {
    private Long id;

    private String field;

    private String type;

    private int price;

    public MenuResponseDto(Long id, String field, String type, int price) {
        this.id = id;
        this.field = field;
        this.type = type;
        this.price = price;
    }

    public MenuResponseDto(Menu menu){
        this.id = menu.getId();
        this.field = menu.getField();
        this.type = menu.getType();
        this.price = menu.getPrice();
    }
}
