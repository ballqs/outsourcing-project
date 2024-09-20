package org.sparta.outsourcingproject.common.exception;

import org.sparta.outsourcingproject.common.dto.ErrorResponse;
import org.sparta.outsourcingproject.common.exception.custom.BadRequestException;
import org.sparta.outsourcingproject.common.exception.custom.ConflictException;
import org.sparta.outsourcingproject.common.exception.custom.ForbiddenException;
import org.sparta.outsourcingproject.common.exception.custom.NotFoundException;
import org.sparta.outsourcingproject.domain.user.exception.DuplicateEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorResponse> BadRequestHandle(BadRequestException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value() , e.getMessage()));
    }

    //이메일 중복
    @ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<ErrorResponse> ConflictHandle(ConflictException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONFLICT.value() , e.getMessage()));
    }

    @ExceptionHandler(value = ForbiddenException.class)
    public ResponseEntity<ErrorResponse> ForbiddenHandle(ForbiddenException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN.value() , e.getMessage()));
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorResponse> NotFoundHandle(NotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value() , e.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidHandle(MethodArgumentNotValidException e) {
        List<String> msgList = new ArrayList<>();
        for (int i = 0; i < e.getBindingResult().getFieldErrors().size(); i++) {
            String msg = e.getBindingResult().getFieldErrors().get(i).getField() + "는 " + e.getBindingResult().getFieldErrors().get(i).getDefaultMessage();
            msgList.add(msg);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value() , String.join(" , " , msgList)));
    }
}
