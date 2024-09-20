//package org.sparta.outsourcingproject.domain.review.repository;
//
//import org.sparta.outsourcingproject.domain.order.service.OrderService;
//import org.sparta.outsourcingproject.domain.review.entity.Review;
//import org.sparta.outsourcingproject.domain.review.service.ReviewService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@RequestMapping("/api")
//public class ReviewController {
//
//    private final ReviewService reviewService;
//    private final OrderService orderService;
//
//
//    public ReviewController(ReviewService reviewService, OrderService orderService) {
//        this.reviewService = reviewService;
//        this.orderService = orderService;
//    }
//
//    // 리뷰 생성
//    @PostMapping("/review")
//    public ResponseEntity<String> createReview(
//            @RequestHeader("Authorization") String token,
//            @RequestBody Review review) {
//
//
//        if (!validateToken(token)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 유효하지 않습니다.");
//        }
//
//        // 배달 완료
//        if (orderService.isDelivered(review.getOrderId())) {
//            reviewService.saveReview(review);
//            return ResponseEntity.ok("리뷰가 성공적으로 작성되었습니다.");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("배달 완료된 주문만 리뷰 작성이 가능합니다.");
//        }
//    }
//
//    // 리뷰 조회
//    @GetMapping("/reviews")
//    public ResponseEntity<List<Review>> getReviews(
//            @RequestHeader("Authorization") String token, // Bearer 토큰
//            @RequestParam(value = "storeId") Long storeId,
//            @RequestParam(value = "minRating", defaultValue = "1") int minRating,
//            @RequestParam(value = "maxRating", defaultValue = "5") int maxRating) {
//
//        // Bearer 토큰 검증 로직
//        if (!validateToken(token)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//
//        // 리뷰 조회
//
//        List<Review> reviews = reviewService.getReviews(storeId, minRating, maxRating);
//        return ResponseEntity.ok(reviews);
//    }
//
//    // Bearer 토큰 검증 메소드 (실제 토큰 검증 로직은 구현 필요)
//    private boolean validateToken(String token) {
//        // Bearer 접두사 제거
//        if (token.startsWith("Bearer ")) {
//            token = token.substring(7);
//            // 토큰 검증 로직 추가 (JWT 검증 등)
//            return true;  // 여기서 실제 검증 수행
//        }
//        return false;
//    }
//}
//
