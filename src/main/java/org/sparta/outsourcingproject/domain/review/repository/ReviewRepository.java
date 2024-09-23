package org.sparta.outsourcingproject.domain.review.repository;


import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.sparta.outsourcingproject.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 가게 ID와 별점 범위를 기준으로 리뷰 조회
    List<Review> findByStoreIdAndStarBetweenOrderByCreatedAtDesc(Long storeId, int minRating, int maxRating);
    // 가게 ID로 리뷰 조회 (최신순 정렬 포함)
//    List<Review> findByCreatedAtDesc(Long storeId);
    List<Review> findByStoreIdOrderByCreatedAtDesc(Long storeId);


}

