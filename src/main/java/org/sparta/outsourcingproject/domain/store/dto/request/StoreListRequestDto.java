package org.sparta.outsourcingproject.domain.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 가게 이름으로 가게 다건 조회 요청 DTO
 */
@Getter
@AllArgsConstructor
public class StoreListRequestDto {

    private String name;

}
