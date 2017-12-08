package com.lyldj.springboot.common.annotation;

/**
 * @description:
 * @author: duanjian
 * @date: 17-11-29 上午11:55
 */
public interface Parser<T> {

    String parse(T t);

}
