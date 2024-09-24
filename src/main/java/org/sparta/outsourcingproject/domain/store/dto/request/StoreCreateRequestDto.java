package org.sparta.outsourcingproject.domain.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.domain.store.enums.StoreOperationStatus;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreCreateRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @NotBlank
    private String tel;

    @NotNull
    private LocalTime openTime;

    @NotNull
    private LocalTime closeTime;

    @NotNull
    private Integer minPrice;

    @NotBlank
    private String address;
    private StoreOperationStatus storeOperationStatus = StoreOperationStatus.OPERATE; // 처음 가게 등록하면 당연히 폐업 아니고 정상 영업 상태
    private BigDecimal rating = BigDecimal.valueOf(0); // 가게 초기 평점 0.0점



}
