package com.lyldj.springboot.common.annotation;

/**
 * @description:
 * @author: duanjian
 * @date: 17-11-29 下午1:31
 */
public class DefaultParser<T> implements Parser<T>{
    @Override
    public String parse(T t) {
        return t.toString();
    }
}
