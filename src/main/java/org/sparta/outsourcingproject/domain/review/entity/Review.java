package org.sparta.outsourcingproject.domain.review.entity;


import jakarta.persistence.*;
import lombok.*;
import org.sparta.outsourcingproject.common.entity.Timestamped;
import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.sparta.outsourcingproject.domain.store.entity.Store;


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

//    private Long storeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

//    private Long orderId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;  // Orders와의 관계 정의

    private int star;  // 1~5 별점
    private String contents;


//    public Review(Long storeId, Long orderId, int star, String contents) {
    public Review(Store store, Orders orders, int star, String contents) {
        this.store = store;
        this.orders = orders;
        this.star = star;
        this.contents = contents;
    }
}

