package org.sparta.outsourcingproject.domain.dibs.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.domain.dibs.entity.Dibs;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DibsResponseDto {

    private Long userId;
    private Long storeId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static DibsResponseDto of(Dibs entity) {
        return new DibsResponseDto(
                entity.getId().getUserId(),
                entity.getId().getStoreId(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }
}
