package org.sparta.outsourcingproject.domain.cart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.domain.menu.entity.Menu;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id" , nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id" , nullable = false)
    private Menu menu;

    public CartDetail(Cart cart , Menu menu , String menuName , int menuPrice , int cnt) {
        this.cart = cart;
        this.menu = menu;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.cnt = cnt;
    }

    public void update(Menu menu , String menuName , int menuPrice , int cnt) {
        this.menu = menu;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.cnt = cnt;
    }
}
