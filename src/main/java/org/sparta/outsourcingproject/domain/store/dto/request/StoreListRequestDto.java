package org.sparta.outsourcingproject.domain.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 가게 이름으로 가게 다건 조회 요청 DTO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreListRequestDto {

    private String name;

}
