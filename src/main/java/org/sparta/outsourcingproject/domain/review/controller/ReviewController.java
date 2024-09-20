package org.sparta.outsourcingproject.domain.review.controller;

import org.sparta.outsourcingproject.domain.review.dto.ReviewRequestDto;
import org.sparta.outsourcingproject.domain.review.dto.ReviewResponseDto;
import org.sparta.outsourcingproject.domain.review.service.ReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/store")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

//    @PostMapping("/{userId}")
//    public String saveReview(@RequestBody ReviewRequestDto reviewRequestDto) {
//
//        return reviewService.saveReview(reviewRequestDto);
//    }
//
//    @GetMapping("/reviews")
//    public List<ReviewResponseDto> getReviews() {
//        return reviewService.getReviews();
//    }
}
