package org.sparta.outsourcingproject.domain.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.common.entity.Timestamped;

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

    private Long menuId; // menu 외래키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id" , nullable = false)
    private Orders orders;
}
