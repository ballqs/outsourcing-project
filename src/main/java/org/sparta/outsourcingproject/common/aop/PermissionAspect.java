package org.sparta.outsourcingproject.common.aop;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.ForbiddenException;
import org.sparta.outsourcingproject.domain.user.Authority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class PermissionAspect {

    @Pointcut("@annotation(org.sparta.outsourcingproject.common.annotation.Permission)")
    public void ownerAuthorizationPointcut() {}

    @Before("ownerAuthorizationPointcut()")
    public void permissionBefore() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Authority authority = (Authority) request.getAttribute("authority");

        if (!authority.equals(Authority.OWNER)) {
            throw new ForbiddenException(ErrorCode.FORBIDDEN_NO_PERMISSION);
        }
    }
}
