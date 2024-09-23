package org.sparta.outsourcingproject.domain.review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.sparta.outsourcingproject.common.entity.Timestamped;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor  // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 포함한 생성자 자동 생성
public class Review extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long storeId;
    private Long orderId;
    private int star;  // 1~5 별점
    private String contents;


    public Review(Long storeId, Long orderId, int star, String contents) {
        this.storeId = storeId;
        this.orderId = orderId;
        this.star = star;
        this.contents = contents;
    }



}

