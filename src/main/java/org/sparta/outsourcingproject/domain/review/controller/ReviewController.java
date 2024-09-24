package org.sparta.outsourcingproject.domain.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.common.dto.ResponseDto;
import org.sparta.outsourcingproject.domain.review.dto.ReviewRequestDto;
import org.sparta.outsourcingproject.domain.review.entity.Review;
import org.sparta.outsourcingproject.domain.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController("/api/store")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity<Review> saveReview(ReviewRequestDto reviewRequestDto) throws IllegalArgumentException {
        reviewRequestDto.validateFields();

        Review review = reviewService.saveReview(reviewRequestDto);
        return ResponseEntity.ok(review);
    }


    @GetMapping("/reviews")
    public ResponseEntity<ResponseDto<List<Review>>> getReviews(
        @RequestParam("storeId") Long storeId,
        @RequestParam("min") int minRating,
        @RequestParam("max") int maxRating
    ) {
        //테스트 용
//        log.info("storeId : {}, min : {}, max : {}", storeId, minRating, maxRating);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value() , reviewService.getReviews(storeId, minRating, maxRating) , "리뷰를 조회했습니다."));
    }
}
