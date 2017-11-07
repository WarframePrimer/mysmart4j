package com.warframe.smart4j.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/25 14:48
 *
 * 定义事务注解，对Service中的方法申明使用事务的方式
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transaction {
}
