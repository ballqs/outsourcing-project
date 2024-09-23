package org.sparta.outsourcingproject.domain.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.sparta.outsourcingproject.domain.store.enums.StoreStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 가게 단건 조회 응답 DTO(가게 메뉴 포함)
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreGetResponseDto {

    private String name;
    private String category;
    private String tel;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Integer minPrice;
    private String address;
    private StoreStatus status; // 개점/마감
    private BigDecimal rating;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<StoreMenuResponseDto> menus;

    public static StoreGetResponseDto of(Store entity) {
        return new StoreGetResponseDto(
                entity.getName(),
                entity.getCategory(),
                entity.getTel(),
                entity.getOpenTime(),
                entity.getCloseTime(),
                entity.getMinPrice(),
                entity.getAddress(),
                entity.getStatus(),
                entity.getRating(),
                entity.getUser().getId(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getMenus().stream()
                        .map(StoreMenuResponseDto::of) // Menu 리스트를 MenuResponseDto 리스트로 변환
                        .collect(Collectors.toList())
        );
    }

}
