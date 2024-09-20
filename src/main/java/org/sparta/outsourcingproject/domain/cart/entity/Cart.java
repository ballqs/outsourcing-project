package org.sparta.outsourcingproject.domain.cart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.sparta.outsourcingproject.common.entity.Timestamped;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int totalAmt;

    private Long userId;  // user 외래키
    private Long storeId; // store 외래키

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "cart", orphanRemoval = true)
    private List<CartDetail> cartDetails = new ArrayList<>();

    public Cart(Long userId, Long storeId, int totalAmt) {
        this.userId = userId;
        this.storeId = storeId;
        this.totalAmt = totalAmt;
    }

    public void updateTotalAmt(int totalAmt) {
        this.totalAmt = totalAmt;
    }
}
