package org.sparta.outsourcingproject.domain.store.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.common.entity.Timestamped;
import org.sparta.outsourcingproject.domain.menu.entity.Menu;
import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.sparta.outsourcingproject.domain.review.entity.Review;
import org.sparta.outsourcingproject.domain.store.enums.StoreOperationStatus;
import org.sparta.outsourcingproject.domain.store.enums.StoreStatus;
import org.sparta.outsourcingproject.domain.user.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
@Getter
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
    public static Store createStore(
            String name,
            String category,
            String tel,
            LocalTime openTime,
            LocalTime closeTime,
            Integer minPrice,
            String address,
            BigDecimal rating,
            User user,
            StoreOperationStatus operationStatus
            )
    {
        Store createdStore = new Store();
        createdStore.name = name;
        createdStore.category = category;
        createdStore.tel = tel;
        createdStore.openTime = openTime;
        createdStore.closeTime = closeTime;
        createdStore.minPrice = minPrice;
        createdStore.address = address;
        createdStore.operationStatus = operationStatus;
        createdStore.rating = rating;
        createdStore.user = user;


        return createdStore;
    }

    //=====연관관계 메서드======//

    public void setUser(User user) {
        this.user = user;
        user.getStores().add(this);
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setReview(this);
    }

    public void addOrder(Orders orders) {
        orderList.add(orders);
        orders.setStore(this);
    }

    public void addMenu(Menu menu) {
        menus.add(menu);
        menu.setStore(this);
    }

    // 현재 시간이 openTime과 closeTime 사이인지 확인
    public boolean isCurrentlyOpened() {
        LocalTime now = LocalTime.now();
        return !now.isBefore(openTime) && now.isBefore(closeTime);
    }

    // 가게의 현재 상태를 업데이트
    public void updateOperationStatus() {
        if (isCurrentlyOpened()) { // 현재 시간이 개점 ~ 마감 시간 사이면
            this.status = StoreStatus.OPENED; // 개점 상태
        } else { // 현재 시간이 마감  ~ 개점 사이면
            this.status = StoreStatus.CLOSED; // 마감 상태
        }
    }
}


