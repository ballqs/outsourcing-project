package org.sparta.outsourcingproject.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSaveRequestDto;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSaveResponseDto;
import org.sparta.outsourcingproject.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;
    @PostMapping("/signIn")
    public ResponseEntity <PostUserSaveResponseDto> signInUser(@RequestBody PostUserSaveRequestDto postUserSaveRequestDto){
        return ResponseEntity.ok(userService.signInUser(postUserSaveRequestDto));
    }
}
