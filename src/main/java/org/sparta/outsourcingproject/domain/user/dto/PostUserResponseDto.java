package org.sparta.outsourcingproject.domain.user.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PostUserResponseDto {
    private HttpStatus status;
    private String message;

    public PostUserResponseDto(HttpStatus status,String message) {
        this.status = status;
        this.message = message;
    }
}