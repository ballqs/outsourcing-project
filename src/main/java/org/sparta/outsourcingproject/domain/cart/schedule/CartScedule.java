package org.sparta.outsourcingproject.domain.cart.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.domain.cart.repository.CartRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class CartScedule {

    private final CartRepository cartRepository;

    // 초 분 시간 일 월 요일
    @Scheduled(cron = "59 59 23 * * *")
    public void scheduled() {
        LocalDate today = LocalDate.now();
        LocalTime ninePM = LocalTime.of(21, 0);
        LocalDateTime todayNinePM = LocalDateTime.of(today, ninePM);
        cartRepository.deleteAllByModifiedAtBefore(todayNinePM);
    }
}
