package org.sparta.outsourcingproject.domain.review.controller;

import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.domain.review.dto.ReviewRequestDto;
import org.sparta.outsourcingproject.domain.review.dto.ReviewResponseDto;
import org.sparta.outsourcingproject.domain.review.entity.Review;
import org.sparta.outsourcingproject.domain.review.repository.ReviewRepository;
import org.sparta.outsourcingproject.domain.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@RestController("/api/store")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping("/review")
    public ResponseEntity<Review> saveReview(ReviewRequestDto reviewRequestDto) throws IllegalArgumentException {
        reviewRequestDto.validateFields();

        Review review = reviewService.saveReview(reviewRequestDto);
        return ResponseEntity.ok(review);
    }
    

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
