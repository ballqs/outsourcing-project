package org.sparta.outsourcingproject.domain.user.dto;

import lombok.Getter;
import org.sparta.outsourcingproject.domain.user.Authority;

@Getter
public class PostUserSignUpRequestDto {
    private String email;
    private String name;
    private String pw;
    private String phoneNumber;
    private Authority authority;
    private String zip;
    private String address;
    private String addressDetail;
}
