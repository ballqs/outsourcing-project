package org.sparta.outsourcingproject.domain.review.controller;

import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.common.dto.ResponseDto;
import org.sparta.outsourcingproject.domain.review.dto.ReviewRequestDto;
import org.sparta.outsourcingproject.domain.review.dto.ReviewResponseDto;
import org.sparta.outsourcingproject.domain.review.entity.Review;
import org.sparta.outsourcingproject.domain.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@RestController("/api/store")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

//    @PostMapping("/{userId}")
//    public ReviewResponseDto saveReview(@RequestBody ReviewRequestDto reviewRequestDto) {
//
//        return reviewService.saveReview(reviewRequestDto);
//    }

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
