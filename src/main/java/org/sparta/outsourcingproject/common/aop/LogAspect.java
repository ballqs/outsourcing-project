package org.sparta.outsourcingproject.common.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.sparta.outsourcingproject.domain.order.repository.OrdersRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class LogAspect {

    private final OrdersRepository ordersRepository;

    @Pointcut("@annotation(org.sparta.outsourcingproject.common.annotation.OrderLog)")
    public void orderLogPointcut() {}

    @Around("orderLogPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // 메소드 실행
            Object result = joinPoint.proceed();
            // 성공 로깅
            LocalDateTime dt = LocalDateTime.now();
            Object[] args = joinPoint.getArgs();
            Orders orders = ordersRepository.findByIdOrThrow((Long) args[1]);
            log.info("요청시각 : {} , 가게 id : {} , 주문 id : {}" , dt , orders.getId() , orders.getStore().getId());
            return result;
        } catch (Exception e) {
            throw e;
        }
    }
}
