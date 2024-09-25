package org.sparta.outsourcingproject.domain.review.repository;


import org.sparta.outsourcingproject.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 가게 ID와 별점 범위를 기준으로 리뷰 조회
    List<Review> findByStoreIdAndStarBetweenOrderByCreatedAtDesc(Long storeId, int minRating, int maxRating);
    // 가게 ID로 리뷰 조회 (최신순 정렬 포함)
    List<Review> findByStoreIdOrderByCreatedAtDesc(Long storeId);

    Optional<Review> findByOrdersId(Long ordersId);
}

