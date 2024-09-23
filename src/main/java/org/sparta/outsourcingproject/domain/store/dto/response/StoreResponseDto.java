package org.sparta.outsourcingproject.domain.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.sparta.outsourcingproject.domain.store.enums.StoreStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 가게 다건 조회 응답 DTO(가게 메뉴 포함 X)
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreResponseDto {

    private Long id;
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

    public static StoreResponseDto of(Store entity) {
        return new StoreResponseDto(
                entity.getId(),
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
                entity.getModifiedAt()
        );
    }

}
