package com.warframe.smart4j.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/14 15:52
 * Action注解类
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {

    /**
     * 请求类型和路径
     */
    String value();
}
