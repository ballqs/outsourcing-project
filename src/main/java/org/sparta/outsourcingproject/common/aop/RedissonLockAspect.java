package org.sparta.outsourcingproject.common.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.sparta.outsourcingproject.common.annotation.RedissonLock;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RedissonLockAspect {

    private final RedissonClient redissonClient;

    @Around("@annotation(org.sparta.outsourcingproject.common.annotation.RedissonLock)")
    public Object redissonLock(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedissonLock annotation = method.getAnnotation(RedissonLock.class);

        // aop를 통한 해당 메소드의 파라미터 값 가져오기
        Object[] args = joinPoint.getArgs();
        String productId = args[0] == null ? "" : args[0].toString();

        String lockKey = productId + annotation.value();

        RLock lock = redissonClient.getLock(lockKey);

        Object result = null; // 리턴 값 저장할 변수

        try {
            boolean lockable = lock.tryLock(annotation.waitTime(), annotation.leaseTime(), TimeUnit.MILLISECONDS);
            if (!lockable) {
                return null;
            }
            result = joinPoint.proceed();
        } catch (InterruptedException e) {
            throw e;
        } finally {
            lock.unlock();
        }
        return result;
    }
}