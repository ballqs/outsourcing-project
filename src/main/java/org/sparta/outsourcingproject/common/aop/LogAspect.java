package org.sparta.outsourcingproject.common.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.sparta.outsourcingproject.domain.order.entity.Orders;
import org.sparta.outsourcingproject.domain.order.repository.OrdersRepository;
import org.sparta.outsourcingproject.domain.order.service.OrdersService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class LogAspect {

    private final OrdersRepository ordersRepository;

    @Pointcut("@annotation(org.sparta.outsourcingproject.common.annotation.OrderLog)")
    public void orderLogPointcut() {}

    @After("orderLogPointcut()")
    public void logAfter(JoinPoint joinPoint) {
        LocalDateTime dt = LocalDateTime.now();
        Object[] args = joinPoint.getArgs();
        Orders orders = ordersRepository.findByIdOrThrow((Long) args[1]);
        // TODO : orders 가 store값이 아닌 storeId를 가지고 있어서 문제가 됨 추후 작업
        log.info("요청시각 : {} , 가게 id : {} , 주문 id : {}" , dt , 0 , 0);
    }
}
