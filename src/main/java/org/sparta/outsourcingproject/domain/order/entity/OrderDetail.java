package org.sparta.outsourcingproject.domain.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.common.entity.Timestamped;
import org.sparta.outsourcingproject.domain.cart.entity.CartDetail;
import org.sparta.outsourcingproject.domain.menu.entity.Menu;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetail extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private int menuPrice;

    @Column(nullable = false)
    private int cnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id" , nullable = false)
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id" , nullable = false)
    private Menu menu;

    public OrderDetail(Orders orders ,CartDetail cartDetail) {
        this.menuName = cartDetail.getMenuName();
        this.menuPrice = cartDetail.getMenuPrice();
        this.cnt = cartDetail.getCnt();
//        this.menuId = cartDetail.getMenuId();
        this.orders = orders;
    }
}
