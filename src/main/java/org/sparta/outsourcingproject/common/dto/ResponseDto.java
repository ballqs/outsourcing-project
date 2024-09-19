package org.sparta.outsourcingproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private int status;
    private T body;
    private String message;
}
