package org.sparta.outsourcingproject.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.sparta.outsourcingproject.domain.review.dto.ReviewResponseDto;
import org.sparta.outsourcingproject.domain.review.entity.Review;
import org.sparta.outsourcingproject.domain.review.repository.ReviewRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }
//
//
//    public ReviewService(ReviewRepository reviewRepository)  {
//        this.reviewRepository = reviewRepository;
//    }
//
//    public ReviewResponseDto saveReview() {
//
//    }


//    public List<ReviewResponseDto> getReviews() {
//
//        return reviewRepository.findAll();
//    }


}
