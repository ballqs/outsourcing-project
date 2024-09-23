package org.sparta.outsourcingproject.domain.review.dto;

import lombok.Getter;
import org.sparta.outsourcingproject.domain.review.entity.Review;

@Getter
public class ReviewResponseDto {
    private Long id;
    private String contents;
    private int star;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.contents = review.getContents();
        this.star = review.getStar();
    }

    public ReviewResponseDto(Long id, String contents, int star) {
        this.id = id;
        this.contents = contents;
        this.star = star;
    }
}
