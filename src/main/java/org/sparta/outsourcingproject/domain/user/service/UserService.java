package org.sparta.outsourcingproject.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.config.JwtUtil;
import org.sparta.outsourcingproject.common.config.PasswordEncoder;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSignInRequestDto;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSignUpRequestDto;
import org.sparta.outsourcingproject.domain.user.exception.DuplicateEmailException;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.sparta.outsourcingproject.domain.user.exception.MismatchPasswordException;
import org.sparta.outsourcingproject.domain.user.exception.UserNotFindException;
import org.sparta.outsourcingproject.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encode;
    private final JwtUtil jwtUtil;

    //회원가입 signUp
    public void signUpUser(PostUserSignUpRequestDto postUserSignUpRequestDto) {
        // 이메일 중복 확인
        boolean overlap = userRepository.existsByEmail(postUserSignUpRequestDto.getEmail());
        if (overlap) {
            throw new DuplicateEmailException(ErrorCode.DUPLICATE_EMAIL_ERROR);
        }

        //비밀번호 벨류체크
        if(!encode.passwordVerification(postUserSignUpRequestDto.getPw())){
            throw new IllegalArgumentException("올바르지 않은 비밀번호 형식입니다.");
        }
        // 비밀번호 암호화
        String pw = encode.encode(postUserSignUpRequestDto.getPw());

        User user = new User(postUserSignUpRequestDto, pw);
        userRepository.save(user);
    }

    //로그인
    public String signInUser(PostUserSignInRequestDto postUserSignUpRequestDto) {
        String email = postUserSignUpRequestDto.getEmail();
        String pw = postUserSignUpRequestDto.getPw();

        User user = findByEmailUser(email);

        checkPw(pw, user.getPw());

        return jwtUtil.createToken(user.getId(), user.getEmail(),user.getAuthority());
    }


    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFindException(ErrorCode.DUPLICATE_EMAIL_ERROR));
    }

    //email값에 맞는 동일한 유저 찾기
    public User findByEmailUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFindException(ErrorCode.USER_NOT_FIND_ERROR));
    }

    private void checkPw(String userPw, String enPw) {
        if (!encode.matches(userPw, enPw)) {
            throw new MismatchPasswordException(ErrorCode.MISMATCH_PASSWORD_ERROR);
        }
    }
}
