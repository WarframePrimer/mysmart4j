package com.warframe.smart4j.annotation;

import java.lang.annotation.*;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/18 16:57
 *
 * 借鉴Spring AOP的风格，实现一个基于切面注解的AOP框架
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 注解
     * 用来定义Controller这类注解
     */
    Class<? extends Annotation> value();

}
