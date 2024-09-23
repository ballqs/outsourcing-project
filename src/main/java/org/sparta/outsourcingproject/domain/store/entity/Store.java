package org.sparta.outsourcingproject.domain.store.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.common.entity.Timestamped;
import org.sparta.outsourcingproject.domain.cart.entity.Cart;
import org.sparta.outsourcingproject.domain.menu.entity.Menu;
import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.sparta.outsourcingproject.domain.review.entity.Review;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreCreateRequestDto;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreUpdateRequestDto;
import org.sparta.outsourcingproject.domain.store.enums.StoreOperationStatus;
import org.sparta.outsourcingproject.domain.store.enums.StoreStatus;
import org.sparta.outsourcingproject.domain.user.entity.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String category;

    @Column(length = 20, nullable = false)
    private String tel;

    LocalTime openTime;

    LocalTime closeTime;

    private Integer minPrice;

    @Column(length = 255, nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private StoreStatus status; // OPEN(개점), CLOSED(마감)

    @Enumerated(EnumType.STRING)
    private StoreOperationStatus operationStatus; // OPERATION(정상 영업), SHUTDOWN(폐점)

    @Column(precision = 2, scale = 1, nullable = false)  // DECIMAL(3,2)와 매핑
    private BigDecimal rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "store")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Orders> orderList = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Menu> menus = new ArrayList<>();



    // 객체 생성 위한 정적 팩토리 메서드
    public static Store createStore(StoreCreateRequestDto requestDto, User user)
    {
        Store createdStore = new Store();
        createdStore.name = requestDto.getName();
        createdStore.category = requestDto.getCategory();
        createdStore.tel = requestDto.getTel();
        createdStore.openTime = requestDto.getOpenTime();
        createdStore.closeTime = requestDto.getCloseTime();
        createdStore.minPrice = requestDto.getMinPrice();
        createdStore.address = requestDto.getAddress();
        createdStore.operationStatus = requestDto.getStoreOperationStatus();
        createdStore.rating = requestDto.getRating();
        createdStore.user = user;
        createdStore.operationStatus = requestDto.getStoreOperationStatus();

        return createdStore;
    }

    //=====연관관계 메서드========//

//    public void setUser(User user) {
//        this.user = user;
//        user.getStores().add(this);
//    }
//
//    public void addReview(Review review) {
//        reviews.add(review);
//        review.setReview(this);
//    }
//
//    public void addOrder(Orders orders) {
//        orderList.add(orders);
//        orders.setStore(this);
//    }
//
//    public void addMenu(Menu menu) {
//        menus.add(menu);
//        menu.setStore(this);
//    }

    // ===================== //


    // 현재 시간이 openTime과 closeTime 사이인지 확인
    public boolean isCurrentlyOpened() {
        LocalTime now = LocalTime.now();

        // 마감 시간이 새벽인 경우(closeTime < openTime)
        if (this.closeTime.isBefore(this.openTime)) {
            return now.isAfter(this.openTime) || now.isBefore(this.closeTime);
        }

        // 마감 시간이 새벽이 아닌 경우(closeTime > openTime)
        return now.isAfter(this.openTime) && now.isBefore(this.closeTime);
    }

    // 가게의 현재 상태(open /close)를 업데이트
    public void updateOperationStatus() {
        if (isCurrentlyOpened()) { // 현재 시간이 개점 ~ 마감 시간 사이면
            this.status = StoreStatus.OPENED; // 개점 상태
        } else { // 현재 시간이 마감  ~ 개점 사이면
            this.status = StoreStatus.CLOSED; // 마감 상태
        }
    }

    // 평점 업데이트 메서드
//    public void updateAverageRating() {
//        // 리뷰가 없을 경우 0으로 처리
//        if (reviews.isEmpty()) {
//            this.rating = BigDecimal.ZERO;  // 평점 0으로 설정
//            return;
//        }
//
//        // 리뷰 평점 합계
//        BigDecimal totalRating = reviews.stream()
//                .map(Review::getRating)  // 각 리뷰의 평점 가져오기
//                .reduce(BigDecimal.ZERO, BigDecimal::add);  // 평점 합산
//
////         평균 계산 (평점의 합을 리뷰 개수로 나누기)
//        BigDecimal averageRating = totalRating.divide(
//                BigDecimal.valueOf(reviews.size()),  // 리뷰 개수
//                2,  // 소수점 2자리로 설정
//                RoundingMode.HALF_UP  // 반올림 모드
//        );
//
//        // 가게의 평점 업데이트
//        this.rating = averageRating;
//    }

    // 가게 폐업
    public void setStoreShutdown() {
        this.operationStatus = StoreOperationStatus.SHUTDOWN;
    }

    // 가게 정보 수정
    public void updateStoreInfo(StoreUpdateRequestDto requestDto) {
        this.name = requestDto.getName() != null ? requestDto.getName() : this.name;
        this.category = requestDto.getCategory() != null ? requestDto.getCategory() : this.category;
        this.tel = requestDto.getTel() != null ? requestDto.getTel() : this.tel;
        this.openTime = requestDto.getOpenTime() != null ? requestDto.getOpenTime() : this.openTime;
        this.closeTime = requestDto.getCloseTime() != null ? requestDto.getCloseTime() : this.closeTime;
        this.minPrice = requestDto.getMinPrice() != null ? requestDto.getMinPrice() : this.minPrice;
        this.address = requestDto.getAddress() != null ? requestDto.getAddress() : this.address;  // 기존 값 유지
    }


}


