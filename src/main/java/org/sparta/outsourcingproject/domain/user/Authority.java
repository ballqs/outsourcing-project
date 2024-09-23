package org.sparta.outsourcingproject.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
public enum Authority {
    OWNER,
    USER
}