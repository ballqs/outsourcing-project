package org.sparta.outsourcingproject.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.config.PasswordEncoder;
import org.sparta.outsourcingproject.common.dto.AuthUser;
import org.sparta.outsourcingproject.domain.user.dto.GetProfileResponseDto;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.sparta.outsourcingproject.domain.user.exception.DuplicatePhoneNumberException;
import org.sparta.outsourcingproject.domain.user.exception.MismatchPasswordException;
import org.sparta.outsourcingproject.domain.user.exception.UserNotActiveException;
import org.sparta.outsourcingproject.domain.user.exception.UserNotFindException;
import org.sparta.outsourcingproject.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCheckService{

    private final UserRepository userRepository;
    private final PasswordEncoder encode;
    //회원조회
    public GetProfileResponseDto getProfile(AuthUser authUser) {
        Long id = authUser.getUserId();
        User user = findUser(id);
        return new GetProfileResponseDto(user);
    }

    //id를 사용한 유저 찾기
    public User findUser(Long userId) {
        return userRepository.findByIdAndStatusTrue(userId)
                .orElseThrow(() -> new UserNotFindException(ErrorCode.USER_NOT_FIND_ERROR));
    }

    //email값에 맞는 동일한 유저 찾기
    public User findByEmailUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFindException(ErrorCode.DUPLICATE_EMAIL_ERROR));
    }

    //pw가 틀렸을때 예외처리
    @Transactional(noRollbackFor = MismatchPasswordException.class)
    public void checkPw(String userPw, String enPw,Long userId) {
        User user = findUser(userId);
        if (!encode.matches(userPw, enPw)) {
            user.missMatchByIncrementState();
            throw new MismatchPasswordException(ErrorCode.MISMATCH_PASSWORD_ERROR);
        }
    }

    //핸드폰번호 중복일때 사용하는 예외 처리
    public void checkHp(String hp){
        Optional<User> user = userRepository.findByPhoneNumber(hp);
        if(user.isPresent() && !hp.equals(user.get().getPhoneNumber())){
            throw new DuplicatePhoneNumberException(ErrorCode.DUPLICATE_PHONE_NUMBER_ERROR);
        }
    }

    //회원 탈퇴 상태일때 로그인시도 예외처리
    public void checkStatus(User user){
        if(!user.isStatus() || user.isProtect()){
            throw new UserNotActiveException(ErrorCode.USER_NOT_FIND_ERROR);
        }
    }
}