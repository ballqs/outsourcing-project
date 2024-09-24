package org.sparta.outsourcingproject.domain.user.dto;

import lombok.Getter;

@Getter
public class PostUserRecoveryRequestDto{
    private String email;
    private String phoneNumber;
}