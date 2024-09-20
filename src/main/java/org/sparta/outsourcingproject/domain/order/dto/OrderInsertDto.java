package org.sparta.outsourcingproject.domain.order.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderInsertDto {
    private Long orderId;

    @NotBlank
    @Email
    private String userEmail;

    private String userName;
    private String userTel;

    @NotBlank
    private String zip;

    @NotBlank
    private String address;

    @NotBlank
    private String addressDetail;

    @NotNull
    private Long userId;

    @NotNull
    private Long storeId;

    @NotNull
    private Long menuId;

    @NotBlank
    private String menuName;

    @NotNull
    private int menuPrice;

    @NotNull
    private int cnt;
}
