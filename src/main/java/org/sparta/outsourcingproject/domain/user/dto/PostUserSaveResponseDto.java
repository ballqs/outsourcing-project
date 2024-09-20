package org.sparta.outsourcingproject.domain.user.dto;

import lombok.Getter;
import org.sparta.outsourcingproject.domain.user.entity.User;

@Getter
public class PostUserSaveResponseDto {
    private String name;
    private String message;

    public PostUserSaveResponseDto(User saveUser) {
        this.name = saveUser.getName();
    }
}
