package org.sparta.outsourcingproject.domain.review.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.domain.review.dto.ReviewRequestDto;
import org.sparta.outsourcingproject.domain.review.dto.ReviewResponseDto;
import org.sparta.outsourcingproject.domain.review.entity.Review;
import org.sparta.outsourcingproject.domain.review.service.ReviewService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@RestController("/api/store")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

//    public ReviewController(ReviewService reviewService) {
//        this.reviewService = reviewService;
//    }

//    @PostMapping("/{userId}")
//    public ReviewResponseDto saveReview(@RequestBody ReviewRequestDto reviewRequestDto) {
//
//        return reviewService.saveReview(reviewRequestDto);
//    }

    @GetMapping("/reviews")
    public List<Review> getReviews(
        @RequestParam("storeId") Long storeId,
        @RequestParam("min") int minRating,
        @RequestParam("max") int maxRating
    ) {
        //테스트 용
//        log.info("storeId : {}, min : {}, max : {}", storeId, minRating, maxRating);
        return reviewService.getReviews(storeId, minRating, maxRating);
    }
}
