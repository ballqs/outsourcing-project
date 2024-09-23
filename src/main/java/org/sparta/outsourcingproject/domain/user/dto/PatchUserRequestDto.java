package org.sparta.outsourcingproject.domain.user.dto;

import lombok.Getter;
import org.sparta.outsourcingproject.domain.user.Authority;

@Getter
public class PatchUserRequestDto {
    private String pw;
    private String newPw;
    private Authority authority;
    private String phoneNumber;
    private String zip;
    private String address;
    private String addressDetail;
}
