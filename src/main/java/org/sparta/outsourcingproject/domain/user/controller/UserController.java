package org.sparta.outsourcingproject.domain.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sparta.outsourcingproject.common.dto.ResponseDto;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSaveRequestDto;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSaveResponseDto;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSignUpRequestDto;
import org.sparta.outsourcingproject.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signIn")
    public ResponseEntity <PostUserSaveResponseDto> signInUser(@Valid @RequestBody PostUserSaveRequestDto postUserSaveRequestDto){
        userService.signInUser(postUserSaveRequestDto);
        return ResponseEntity.ok(new PostUserSaveResponseDto(HttpStatus.OK, "회원가입에 성공했습니다"));
    }

    @PostMapping("/signUp")
    public ResponseEntity<PostUserSaveResponseDto> signUpUser(@Valid @RequestBody PostUserSignUpRequestDto postUserSignUpRequestDto, HttpServletResponse response){
        String token = userService.signUpUser(postUserSignUpRequestDto);
        response.addHeader("ACCESS_TOKEN",token);
        return ResponseEntity.ok(new PostUserSaveResponseDto(HttpStatus.OK, "로그인에 성공했습니다."));
    }

}
