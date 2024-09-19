package org.sparta.outsourcingproject.domain.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.sparta.outsourcingproject.domain.store.enums.StoreStatus;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class StoreResponseDto {

    private String name;
    private String category;
    private String tel;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Integer minPrice;
    private String address;
    private StoreStatus status; // 개점/마감
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static StoreResponseDto of(Store entity) {
        return new StoreResponseDto(
                entity.getName(),
                entity.getCategory(),
                entity.getTel(),
                entity.getOpenTime(),
                entity.getCloseTime(),
                entity.getMinPrice(),
                entity.getAddress(),
                entity.getStatus(),
                entity.getUser().getId(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

}
