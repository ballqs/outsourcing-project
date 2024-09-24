package org.sparta.outsourcingproject.domain.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.common.annotation.Auth;
import org.sparta.outsourcingproject.common.dto.AuthUser;
import org.sparta.outsourcingproject.common.dto.ResponseDto;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSignInRequestDto;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSignUpRequestDto;
import org.sparta.outsourcingproject.domain.user.dto.*;
import org.sparta.outsourcingproject.domain.user.service.UserCheckService;
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
    private final UserCheckService userCheckService;

    //회원가입 sign up
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<String>> signUpUser(@RequestBody PostUserSignUpRequestDto postUserSignUpRequestDto){
        userService.signUpUser(postUserSignUpRequestDto);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value() , null , "회원가입에 성공했습니다"));
    }

    //로그인 sign in
    @PostMapping("/signin")
    public ResponseEntity<ResponseDto<String>> signInUser(@Valid @RequestBody PostUserSignInRequestDto postUserSignInRequestDto, HttpServletResponse response){
        String token = userService.signInUser(postUserSignInRequestDto);
        response.addHeader("ACCESS_TOKEN",token);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value() , null , "로그인에 성공했습니다."));
    }

    //잠금 해제
    @PostMapping("/recovery")
    public ResponseEntity<ResponseDto<String>> recoveryUser(@RequestBody PostUserRecoveryRequestDto requestDto){
        userService.recoveryUser(requestDto);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), null, "잠금이 해제 되었습니다."));
    }

    //회원탈퇴 delete
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto<String>> deleteUser(@Auth AuthUser authUser,@RequestBody DeleteUserRequestDto deleteUserRequestDto){
        userService.deleteUser(authUser,deleteUserRequestDto);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value() , null , "회원 탈퇴하였습니다."));
    }

    //회원정보 수정
    @PatchMapping("/update")
    public ResponseEntity<ResponseDto<String>> updateUser(@Auth AuthUser authUser,@Valid @RequestBody PatchUserRequestDto patchUserRequestDto) {
        userService.updateUser(authUser,patchUserRequestDto);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(),null,"수정이 완료 되었습니다."));
    }

    //회원정보 조회
    @GetMapping
    public ResponseEntity<ResponseDto<GetProfileResponseDto>> getProfileUser(@Auth AuthUser authUser){
        GetProfileResponseDto responseDto = userCheckService.getProfile(authUser);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), responseDto,"조회 되었습니다."));
    }
}