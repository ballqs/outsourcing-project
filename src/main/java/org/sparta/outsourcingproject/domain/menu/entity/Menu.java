package org.sparta.outsourcingproject.domain.menu.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.common.entity.Timestamped;
import org.sparta.outsourcingproject.domain.menu.common.FoodType;
import org.sparta.outsourcingproject.domain.menu.dto.request.MenuEditRequestDto;
import org.sparta.outsourcingproject.domain.menu.dto.request.MenuRequestDto;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoodType type;

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

    public void update(MenuEditRequestDto menuEditRequestDto) {
        this.name = menuEditRequestDto.getName();
        this.type = menuEditRequestDto.getType();
        this.price = menuEditRequestDto.getPrice();
        this.soldOut = menuEditRequestDto.isSoldOut();
    }

    public Menu(MenuRequestDto menuRequestDto, Store store) {
        this.name = menuRequestDto.getName();
        this.type = menuRequestDto.getType();
        this.price = menuRequestDto.getPrice();
        this.store = store;
        this.soldOut = false;
    }
}
