package org.sparta.outsourcingproject.domain.user.dto;

import lombok.Getter;

@Getter
public class PostUserSignUpRequestDto {
    private String email;
    private String pw;
}
