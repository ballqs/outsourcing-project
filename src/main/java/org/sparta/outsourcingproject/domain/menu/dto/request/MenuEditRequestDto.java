package org.sparta.outsourcingproject.domain.menu.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.domain.menu.common.FoodType;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuEditRequestDto {

    @NotEmpty(message = "메뉴 이름은 필수 입력 값입니다.")
    private String name;

    @NotNull(message = "메뉴 종류는 필수 입력 값입니다.")
    private FoodType type;

    @Positive(message = "메뉴 가격은 양수값이어야 합니다.")
    private int price;

    @NotEmpty(message = "품절 여부")
    private boolean soldOut;
}
