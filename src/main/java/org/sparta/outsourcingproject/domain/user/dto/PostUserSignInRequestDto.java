package org.sparta.outsourcingproject.domain.user.dto;

import lombok.Getter;
import org.sparta.outsourcingproject.domain.user.Authority;

@Getter
public class PostUserSignInRequestDto {
    private String email;
    private String pw;
    private Authority authority;
}