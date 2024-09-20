package org.sparta.outsourcingproject.domain.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 가게 단건 조회 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreGetRequestDto {

    private String name;

}
