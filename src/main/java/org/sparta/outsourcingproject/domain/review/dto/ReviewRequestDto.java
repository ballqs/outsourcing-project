package org.sparta.outsourcingproject.domain.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {

    private Long orderId;   // 주문 ID >> long
    private int rating;     // 별점 (1~5점)
    private String comment; // 리뷰 내용

    // 별점 유효성 검사 메서드
    public void validateRating() {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("별점은 1~5점 사이여야 합니다.");
        }
    }

    public void validateFields() throws IllegalArgumentException {
//        Long orderId = this.orderId;
//        if (orderId == null) throw new IllegalArgumentException("주문 번호가 존재하지 않습니다.\n 다시 입력해주세요");

//        String comment = this.comment;
//        if (comment.isBlank() && comment.length() < 10) throw new IllegalArgumentException("리뷰가 너무 짧습니다. 10글자 이상 작성해주세요.");
    }

}

