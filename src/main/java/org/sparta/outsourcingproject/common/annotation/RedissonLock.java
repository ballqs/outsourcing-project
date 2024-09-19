package org.sparta.outsourcingproject.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedissonLock {
    String value();
    long waitTime() default 5000L;
    long leaseTime() default 2000L;
}