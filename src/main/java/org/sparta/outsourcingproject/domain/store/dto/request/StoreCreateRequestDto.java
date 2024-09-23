package org.sparta.outsourcingproject.domain.store.dto.request;

import jakarta.validation.constraints.NotBlank;
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

    private String name;
    private String category;
    private String tel;
    private LocalTime openTime;
    private LocalTime closeTime;

    @NotBlank
    private Integer minPrice;
    private String address;
    private StoreOperationStatus storeOperationStatus = StoreOperationStatus.OPERATE; // 처음 가게 등록하면 당연히 폐업 아니고 정상 영업 상태
    private BigDecimal rating = BigDecimal.valueOf(0); // 가게 초기 평점 0.0점



}
