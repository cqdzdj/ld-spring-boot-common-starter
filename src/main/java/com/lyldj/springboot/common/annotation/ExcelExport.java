package com.lyldj.springboot.common.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: duanjian
 * @date: 17-11-21 下午5:16
 * @description: excel导出注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelExport {

    @AliasFor("header")
    String value() default "";

    @AliasFor("value")
    String header() default "";

    String nullValue() default "";

    int order() default 0;

    boolean highlight() default false;

}
