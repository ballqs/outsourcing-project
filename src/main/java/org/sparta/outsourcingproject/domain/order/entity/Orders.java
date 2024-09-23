package org.sparta.outsourcingproject.domain.order.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.sparta.outsourcingproject.common.entity.Timestamped;
import org.sparta.outsourcingproject.domain.cart.entity.Cart;
import org.sparta.outsourcingproject.domain.review.entity.Review;
import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.sparta.outsourcingproject.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Orders extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String userTel;

    @Column(nullable = false , length = 50)
    private String zip;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrdersProcessEnum ordersProcess;

    @Column(nullable = false)
    private int totalAmt;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "orders" , orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id" , nullable = false)
    private Store store;

    @OneToOne(mappedBy = "orders")
    private Review review;

    public void updateOrdersProcess(OrdersProcessEnum ordersProcess) {
        this.ordersProcess = ordersProcess;
    }

    public static Orders CreateOrders(User user , Cart cart) {
        Orders orders = new Orders();
        orders.userTel = user.getPhoneNumber();
        orders.zip = user.getZip();
        orders.address = user.getAddress();
        orders.addressDetail = user.getAddressDetail();
        orders.ordersProcess = OrdersProcessEnum.ORDER;
        orders.totalAmt = cart.getTotalAmt();
        orders.user = cart.getUser();
        orders.store = cart.getStore();
        return orders;
    }
}
