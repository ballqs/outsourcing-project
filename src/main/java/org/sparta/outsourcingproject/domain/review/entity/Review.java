package org.sparta.outsourcingproject.domain.review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor  // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 포함한 생성자 자동 생성
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long storeId;
    private Long orderId;
    private String star;  // 1~5점
    private String comment;
    private LocalDateTime createdAt = LocalDateTime.now();  // 생성 시점 자동 기록

    // 별도의 생성자가 필요한 경우
    public Review(Long storeId, Long orderId, String star, String comment) {
        this.storeId = storeId;
        this.orderId = orderId;
        this.star = star;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();  // 생성 시간 자동 저장
    }
}

