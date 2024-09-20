package org.sparta.outsourcingproject.domain.user.dto;

import lombok.Getter;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.springframework.http.HttpStatus;

@Getter
public class PostUserSaveResponseDto {
    private HttpStatus status;
    private String message;

    public PostUserSaveResponseDto(HttpStatus status,String message) {
        this.status = status;
        this.message = message;
    }
}
