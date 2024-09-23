package org.sparta.outsourcingproject.domain.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.sparta.outsourcingproject.domain.store.enums.StoreStatus;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreCreateResponseDto {

    private String name;
    private String category;
    private String tel;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Integer minPrice;
    private String address;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private StoreStatus status;

    public static StoreCreateResponseDto of(Store entity) {
        return new StoreCreateResponseDto(
                entity.getName(),
                entity.getCategory(),
                entity.getTel(),
                entity.getOpenTime(),
                entity.getCloseTime(),
                entity.getMinPrice(),
                entity.getAddress(),
                entity.getUser().getId(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getStatus()
        );
    }

}
