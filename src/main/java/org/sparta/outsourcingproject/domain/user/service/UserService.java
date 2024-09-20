package org.sparta.outsourcingproject.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.sparta.outsourcingproject.common.config.PasswordEncoder;
import org.sparta.outsourcingproject.common.exception.custom.DuplicateEmailException;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSaveRequestDto;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSaveResponseDto;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.sparta.outsourcingproject.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encode;


    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user를 찾을 수 없습니다."));
    }


    public PostUserSaveResponseDto signInUser(PostUserSaveRequestDto postUserSaveRequestDto) {
        // 이메일 중복 확인
        boolean overlap = userRepository.existsByEmail(postUserSaveRequestDto.getEmail());
        if (overlap) {
            throw new DuplicateEmailException("이미 사용중인 ID 입니다.");
        }

        // 비밀번호 암호화
        String pw = encode.encode(postUserSaveRequestDto.getPw());

        User user = new User(postUserSaveRequestDto, pw);

        User saveUser = userRepository.save(user);

        return new PostUserSaveResponseDto(saveUser);
    }
}
