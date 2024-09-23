package org.sparta.outsourcingproject.domain.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.common.annotation.Auth;
import org.sparta.outsourcingproject.common.dto.AuthUser;
import org.sparta.outsourcingproject.domain.user.dto.DeleteReqestDto;
import org.sparta.outsourcingproject.domain.user.dto.PostUserResponseDto;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSignInRequestDto;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSignUpRequestDto;
import org.sparta.outsourcingproject.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    //회원가입 sign up
    @PostMapping("/signup")
    public ResponseEntity <PostUserResponseDto> signUpUser(@RequestBody PostUserSignUpRequestDto postUserSignUpRequestDto){
        userService.signUpUser(postUserSignUpRequestDto);
        return ResponseEntity.ok(new PostUserResponseDto(HttpStatus.OK, "회원가입에 성공했습니다"));
    }

    //로그인 sign in
    @PostMapping("/signin")
    public ResponseEntity<PostUserResponseDto> signInUser(@Valid @RequestBody PostUserSignInRequestDto postUserSignInRequestDto, HttpServletResponse response){
        String token = userService.signInUser(postUserSignInRequestDto);
        response.addHeader("ACCESS_TOKEN",token);
        return ResponseEntity.ok(new PostUserResponseDto(HttpStatus.OK, "로그인에 성공했습니다."));
    }

    //회원탈퇴 delete
    @DeleteMapping("/delete")
    public ResponseEntity<PostUserResponseDto> deleteUser(@Auth AuthUser authUser,@RequestBody DeleteReqestDto deleteReqestDto){
        userService.deleteUser(authUser,deleteReqestDto);
        return ResponseEntity.ok(new PostUserResponseDto(HttpStatus.OK, "회원 탈퇴하였습니다."));
    }
}