package org.sparta.outsourcingproject.domain.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sparta.outsourcingproject.domain.menu.entity.Menu;

/**
 * 해당 가게의 메뉴 DTO(가게 단건 조회에 포함됨)
 */
@Getter
@AllArgsConstructor
public class StoreMenuResponseDto {

    private Long id;
    private String name;
    private Integer price;

    public static StoreMenuResponseDto of(Menu menu) {
        return new StoreMenuResponseDto(
                menu.getId(),
                menu.getName(),
                menu.getPrice()
        );
    }
}
