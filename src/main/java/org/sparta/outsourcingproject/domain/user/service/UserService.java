package org.sparta.outsourcingproject.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.config.JwtUtil;
import org.sparta.outsourcingproject.common.config.PasswordEncoder;
import org.sparta.outsourcingproject.common.dto.AuthUser;
import org.sparta.outsourcingproject.domain.user.dto.DeleteReqestDto;
import org.sparta.outsourcingproject.domain.user.dto.PostUserResponseDto;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSignInRequestDto;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSignUpRequestDto;
import org.sparta.outsourcingproject.domain.user.exception.DuplicateEmailException;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.sparta.outsourcingproject.domain.user.exception.MismatchPasswordException;
import org.sparta.outsourcingproject.domain.user.exception.UserNotFindException;
import org.sparta.outsourcingproject.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        checkStatus(user);
        return jwtUtil.createToken(user.getId(), user.getEmail(),user.getAuthority());
    }

    //회원 탈퇴
    @Transactional
    public void deleteUser(AuthUser authUser, DeleteReqestDto deleteReqestDto) {
        Long id = authUser.getUserId();
        User user = findUser(id);

        //비밀번호 체크
        String pw= deleteReqestDto.getPw();
        checkPw(pw, user.getPw());
        //유저 비활성화 코드
        user.delete();
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

    private void checkStatus(User user){
        if(!user.isStatus()){
            throw new UserNotActiveException(ErrorCode.USER_NOT_FIND_ERROR);
        }
    }
}