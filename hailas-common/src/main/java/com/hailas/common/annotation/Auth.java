package com.hailas.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wulin on 2017/1/9.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Auth {
    String[] hasPermission() default {};
    String[] hasAnyPermission() default {};
    String[] hasRole() default {};
    String[] hasAnyRole() default {};
}
