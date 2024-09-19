package org.sparta.outsourcingproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthUser {
    private Long userId;
    private String email;
}

