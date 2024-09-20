package org.sparta.outsourcingproject.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.common.entity.Timestamped;
import org.sparta.outsourcingproject.domain.cart.entity.Cart;
import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSaveRequestDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;
    private String name;
    private String pw;

    @Column(name = "phoneNumber", unique = true)
    private String phoneNumber;

    // 여기는 ENUM 사용해주시길 바랍니다.
    @Column(nullable = false)
    private String authority;

    @Column(nullable = false)
    private String zip;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String addressDetail;

    // 탈퇴여부
    @Column(nullable = false)
    private boolean status;

    @OneToMany(mappedBy = "user" , orphanRemoval = true)
    private List<Store> storeList = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @OneToMany(mappedBy = "user" , orphanRemoval = true)
    private List<Orders> orders = new ArrayList<>();

    public User(PostUserSaveRequestDto requestDto, String pw) {
        this.email = requestDto.getEmail();
        this.pw = pw;
        this.name = requestDto.getName();
        this.phoneNumber = requestDto.getPhoneNumber();
    }
}
