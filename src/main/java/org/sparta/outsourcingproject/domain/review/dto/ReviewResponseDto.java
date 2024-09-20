package org.sparta.outsourcingproject.domain.review.dto;

import lombok.Getter;
import org.sparta.outsourcingproject.domain.review.entity.Review;

@Getter
public class ReviewResponseDto {
    private Long id;
    private String username;
    private String contents;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
//        this.username = review.getUsername();
//        this.contents = review.getContents();
    }

    public ReviewResponseDto(Long id, String username, String contents) {
        this.id = id;
        this.username = username;
        this.contents = contents;
    }
}
