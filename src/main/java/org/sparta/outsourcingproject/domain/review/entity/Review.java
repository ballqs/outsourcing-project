package org.sparta.outsourcingproject.domain.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.common.entity.Timestamped;
import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.sparta.outsourcingproject.domain.store.entity.Store;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor  // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 포함한 생성자 자동 생성
public class Review extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String star;  // 1~5점
    private String comment;
    private LocalDateTime createdAt = LocalDateTime.now();  // 생성 시점 자동 기록

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stord_id" , nullable = false)
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    // 별도의 생성자가 필요한 경우
    public Review(Long storeId, Long orderId, String star, String comment) {
//        this.storeId = storeId;
//        this.orderId = orderId;
        this.star = star;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();  // 생성 시간 자동 저장
    }
}