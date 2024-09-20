package org.sparta.outsourcingproject.domain.cart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "cart_detail")
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private int menuPrice;

    @Column(nullable = false)
    private int cnt;

    private Long menuId; // menu 외래키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id" , nullable = false)
    private Cart cart;

    public CartDetail(Cart cart , Long menuId , String menuName , int menuPrice , int cnt) {
        this.cart = cart;
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.cnt = cnt;
    }

    public void update(Long menuId , String menuName , int menuPrice , int cnt) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.cnt = cnt;
    }
}
