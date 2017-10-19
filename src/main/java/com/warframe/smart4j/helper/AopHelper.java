package com.warframe.smart4j.helper;

import com.warframe.smart4j.annotation.Aspect;
import com.warframe.smart4j.proxy.AspectProxy;
import com.warframe.smart4j.proxy.Proxy;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/18 21:09
 * <p>
 * 实现AOP
 *
 * 方法拦截助手类
 */

public final class AopHelper {

    /**
     * 获取Aspect注解中设置的注解类，若该注解类不是Aspect类，则可调用ClassHelper#getClassSetByAnnotation方法获取相关类
     * 将这些类放入目标集合中，最后返回这个目标集合类。
     *
     * @param aspect
     * @return
     * @throws Exception
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();

        if (annotation != null && !annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }

        return targetClassSet;
    }

    /**
     * 一个代理类可以对应一个或多个目标类
     *
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
        return proxyMap;
    }

    /**
     * 代理类需要扩展AspectProxy抽象类，还需要带有Aspect注解，只有满足上述两个条件
     * 才能根据Aspect注解所定义的注解属性去获取该注解所对应的目标类集合
     * 一旦获取代理类与目标类之间的映射关系，就能根据这个关系分析出目标类与代理对象之间的映射关系
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap() throws Exception {
        Map<Class<?>,List<Proxy>> targetMap = new HashMap<>();
        //TODO


        return targetMap;
    }


}
