package org.sparta.outsourcingproject.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.common.entity.Timestamped;
import org.sparta.outsourcingproject.domain.cart.entity.Cart;
import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.sparta.outsourcingproject.domain.user.Authority;
import org.sparta.outsourcingproject.domain.user.dto.PatchUserRequestDto;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSignUpRequestDto;

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
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String pw;
    @Column(name = "phoneNumber", unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Authority authority;

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

    public User(PostUserSignUpRequestDto requestDto, String pw) {
        this.email = requestDto.getEmail();
        this.name = requestDto.getName();
        this.pw = pw;
        this.zip = requestDto.getZip();
        this.address = requestDto.getAddress();
        this.addressDetail = requestDto.getAddressDetail();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.authority = requestDto.getAuthority();
        this.status = true;
    }

    public void delete() {
        this.status = false;
    }

    public void update(String pw, PatchUserRequestDto requestDto) {
        this.pw = pw;
        this.authority = requestDto.getAuthority();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.address = requestDto.getAddress();
        this.addressDetail = requestDto.getAddressDetail();
        this.zip = requestDto.getZip();
    }
}
