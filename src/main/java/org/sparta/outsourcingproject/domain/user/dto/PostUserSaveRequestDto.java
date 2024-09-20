package org.sparta.outsourcingproject.domain.user.dto;

import lombok.Getter;

@Getter
public class PostUserSaveRequestDto {
    private String email;
    private String name;
    private String pw;
    private String phoneNumber;
    private String address;
    private String detailAddress;
}
