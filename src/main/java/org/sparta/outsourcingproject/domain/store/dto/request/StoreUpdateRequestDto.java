package org.sparta.outsourcingproject.domain.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

/**
 * 가게 정보 수정 요청 Dto
 */
@Getter
@AllArgsConstructor
public class StoreUpdateRequestDto {

    private String name;
    private String category;
    private String tel;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Integer minPrice;
    private String address;


}
