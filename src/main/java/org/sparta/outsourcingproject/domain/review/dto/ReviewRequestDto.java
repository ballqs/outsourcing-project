package org.sparta.outsourcingproject.domain.review.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {

    @Positive
    @NotNull
    private Long orderId;   // 주문 ID >> long
    @Min(1)
    @Max(5)
    @NotNull
    private Integer rating;     // 별점 (1~5점)
    @Size(min = 10 , max = 200)
    @NotBlank
    private String comment; // 리뷰 내용
}

