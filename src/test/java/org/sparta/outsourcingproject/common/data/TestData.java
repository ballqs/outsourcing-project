package org.sparta.outsourcingproject.common.data;

import org.sparta.outsourcingproject.common.config.JwtUtil;
import org.sparta.outsourcingproject.common.dto.AuthUser;
import org.sparta.outsourcingproject.domain.user.Authority;

public class TestData {

    public static final AuthUser AUTH_USER = new AuthUser(1L , "test@test.com" , Authority.USER);
    public static String TOKEN;

    public static void initToken(JwtUtil jwtUtil) {
        TOKEN = jwtUtil.createToken(1L , "test@test.com" , Authority.USER);
    }
}