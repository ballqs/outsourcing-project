package org.sparta.outsourcingproject.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.config.JwtUtil;
import org.sparta.outsourcingproject.common.config.PasswordEncoder;
import org.sparta.outsourcingproject.common.dto.AuthUser;
import org.sparta.outsourcingproject.domain.user.dto.*;
import org.sparta.outsourcingproject.domain.user.exception.*;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.sparta.outsourcingproject.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encode;
    private final JwtUtil jwtUtil;
    private final UserCheckService userCheckService;

    //회원가입 signUp
    public void signUpUser(PostUserSignUpRequestDto postUserSignUpRequestDto) {
        // 이메일 중복 확인
        boolean overlap = userRepository.existsByEmail(postUserSignUpRequestDto.getEmail());
        if (overlap) {
            throw new DuplicateEmailException(ErrorCode.DUPLICATE_EMAIL_ERROR);
        }
//        checkHp(postUserSignUpRequestDto.getPhoneNumber());
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
    @Transactional(noRollbackFor = MismatchPasswordException.class)
    public String signInUser(PostUserSignInRequestDto postUserSignUpRequestDto) {

        User user = userCheckService.findByEmailUser(postUserSignUpRequestDto.getEmail());

        userCheckService.checkPw(postUserSignUpRequestDto.getPw(), user.getPw(), user.getId());

        userCheckService.checkStatus(user);

        return jwtUtil.createToken(user.getId(), user.getEmail(),user.getAuthority());
    }

    //회원 탈퇴
    @Transactional
    public void deleteUser(AuthUser authUser, DeleteUserRequestDto deleteReqestDto) {
        Long id = authUser.getUserId();
        User user = userCheckService.findUser(id);

        //비밀번호 체크
        String pw= deleteReqestDto.getPw();
        userCheckService.checkPw(pw, user.getPw(), user.getId());
        //유저 비활성화 코드
        user.delete();
    }

    //회원 수정
    @Transactional
    public void updateUser(AuthUser authUser, PatchUserRequestDto requestDto){
        Long id = authUser.getUserId();
        User user = userCheckService.findUser(id);

        String pw= requestDto.getPw();
        userCheckService.checkPw(pw, user.getPw(), user.getId());

        if(!encode.passwordVerification(requestDto.getPw())){
            throw new IllegalArgumentException("올바르지 않은 비밀번호 형식입니다.");
        }

        if(requestDto.getPw().equals(requestDto.getNewPw())){
            throw new SamePasswordException(ErrorCode.SAME_PASSWORD);
        }
        //중복된 핸드폰번호 일 경우 예외
        userCheckService.checkHp(requestDto.getPhoneNumber());

        String encodePw = encode.encode(requestDto.getNewPw());
        user.update(encodePw,requestDto);
    }


    public void recoveryUser(PostUserRecoveryRequestDto requestDto) {
    }
}