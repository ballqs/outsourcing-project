package org.sparta.outsourcingproject.domain.review.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.sparta.outsourcingproject.domain.order.service.OrdersService;
import org.sparta.outsourcingproject.domain.review.dto.ReviewRequestDto;
import org.sparta.outsourcingproject.domain.review.dto.ReviewResponseDto;
import org.sparta.outsourcingproject.domain.review.entity.Review;
import org.sparta.outsourcingproject.domain.review.repository.ReviewRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Component
@RequiredArgsConstructor
@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrdersService ordersService;

    public void saveReview(ReviewRequestDto reviewRequestDto) {
        // ReviewRequestDto에서 주문 ID를 가져옴
        Long orderId = reviewRequestDto.getOrderId();

        // 주문이 배달 완료 상태인지 확인
        if (!ordersService.deliveredCheck(orderId)) {
            throw new IllegalArgumentException("배달 완료된 주문만 리뷰 작성이 가능합니다.");
        }

        Optional<Review> reviewCheck = reviewRepository.findByOrdersId(orderId);
        if (reviewCheck.isPresent()) {
            throw new IllegalArgumentException("이미 리뷰가 작성된 주문입니다.");
        }

        Orders orders = ordersService.findOrders(orderId);

        // 리뷰 엔티티로 변환
        Review review = new Review(
                orders.getStore(),
                orders,
                reviewRequestDto.getRating(),
                reviewRequestDto.getComment()
        );

        // 리뷰 저장
        reviewRepository.save(review);
    }

    public List<ReviewResponseDto> getReviews(Long storeId, int minRating, int maxRating) {
        //리뷰 조회
        List<Review> reviews = reviewRepository.findByStoreIdAndStarBetweenOrderByCreatedAtDesc(storeId, minRating, maxRating);
        log.info("reviews : {}", reviews);

        //최신순
        return reviews.stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }
}
