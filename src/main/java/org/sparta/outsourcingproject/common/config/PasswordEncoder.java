package org.sparta.outsourcingproject.common.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordEncoder {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    // 비밀번호 단방향 암호화
    public String encode(String rawPassword) {
        if (!passwordVerification(rawPassword)) {
            throw new IllegalArgumentException("비밀번호는 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함하며, 최소 8글자 이상이어야 합니다.");
        }
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    // 비밀번호 일치하는지 검증
    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }

    // 비밀번호 정규식 검증 ^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*(),.?":{}|<>]).{8,}$
    public boolean passwordVerification(String password) {
        return pattern.matcher(password).matches();
    }
}
