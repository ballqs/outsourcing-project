package org.sparta.outsourcingproject.domain.menu.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.common.entity.Timestamped;
import org.sparta.outsourcingproject.domain.store.entity.Store;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String type;        // enum 으로 한식, 양식, 중식, 일식, 분식 등 처리해보기

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private boolean soldOut;

    @Column(nullable = false)  // 삭제 여부 (soft delete) default = false;
    private boolean status = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    public void delete() {
        this.status = true;
    }

    public void update(String name, String type, int price, boolean soldOut) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.soldOut = soldOut;
    }

    public Menu(String name, String type, int price) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.soldOut = false;
    }
}
