package org.sparta.outsourcingproject.domain.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.domain.store.enums.StoreOperationStatus;

import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * 가게 등록 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreCreateRequestDto {

    @NotBlank
    private String name;
    private String category;
    private String tel;

    @NotBlank
    private LocalTime openTime;

    @NotBlank
    private LocalTime closeTime;

    @NotBlank
    private Integer minPrice;
    private String address;
    private StoreOperationStatus storeOperationStatus = StoreOperationStatus.OPERATE;
    private BigDecimal rating = BigDecimal.valueOf(0);

}
