package org.sparta.outsourcingproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sparta.outsourcingproject.domain.user.Authority;

@Getter
@AllArgsConstructor
public class AuthUser {
    private Long userId;
    private String email;
    private Authority authority;
}

