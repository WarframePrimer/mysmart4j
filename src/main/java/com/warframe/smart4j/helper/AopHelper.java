package com.warframe.smart4j.helper;

import com.warframe.smart4j.annotation.Aspect;
import com.warframe.smart4j.annotation.Service;
import com.warframe.smart4j.proxy.AspectProxy;
import com.warframe.smart4j.proxy.Proxy;
import com.warframe.smart4j.proxy.ProxyManager;
import com.warframe.smart4j.proxy.TransactionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/18 21:09
 * <p>
 * 实现AOP
 * <p>
 * 方法拦截助手类
 * 在AopHelper中需要获取所有的targetClass及其被拦截的切面类(Proxy)实例(proxyList)，并通过ProxyManager#createProxy方法创建代理对象
 * 获取targetClass及其对应的proxyList
 * 加载Aop框架
 */

public final class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    //静态代码块到初始化整个AOP框架
    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);

            //将代理bean放入BEAN_MAP中
            for (Map.Entry<Class<?>, List<Proxy>> entry : targetMap.entrySet()) {
                Class<?> targetClass = entry.getKey();
                List<Proxy> proxyList = entry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }

        } catch (Exception e) {
            LOGGER.error("aop failure");
        }
    }


    /**
     * 获取Aspect注解中设置的注解类，若该注解类不是Aspect类，则可调用ClassHelper#getClassSetByAnnotation方法获取相关类
     * 将这些类放入目标集合中，最后返回这个目标集合类。
     * <p>
     * 获取到要代理的targetClass，然后放入targetClassSet中
     *
     * @param aspect
     * @return
     * @throws Exception
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = new HashSet<>();
        //获取Aspect中设置的注解类
        Class<? extends Annotation> annotation = aspect.value();

        if (annotation != null && !annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }

        return targetClassSet;
    }

    /**
     * 一个代理类可以对应一个或多个目标类
     * 这里的代理类指定是切面类
     *
     * 更改，增加了事务代理，代理映射中不仅包括普通的切面代理，还增加了事务代理，
     * 所以createProxyMap需要添加两种不同的代理映射
     *
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    private static void addAspectProxy(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        //获取到所有的代理类(实现了AspectProxy的具体子类)
        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
    }

    private static void addTransactionProxy(Map<Class<?>,Set<Class<?>>> proxyMap){
        Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class,serviceClassSet);
    }


    /**
     * 代理类需要扩展AspectProxy抽象类，还需要带有Aspect注解，只有满足上述两个条件
     * 例子
     *
     * @return
     * @throws Exception
     * @Aspect(Controller.class) //获取targetClass
     * public xxxProxy extends AspectProxy {  //proxyClass
     * ...
     * }
     * 才能根据Aspect注解所定义的注解属性去获取该注解所对应的目标类集合
     * 一旦获取代理类与目标类之间的映射关系，就能根据这个关系分析出目标类与代理对象之间的映射关系(proxyMap(proxyClass,Class<?> targetClassSet))
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();

        //通过代理类和目标类的映射关系(proxyMap)，反过来获得目标类和代理对象之间的映射关系 targetMap(targetClass,proxyList)
        for (Map.Entry<Class<?>, Set<Class<?>>> entry : proxyMap.entrySet()) {
            Class<?> proxyClass = entry.getKey();
            Set<Class<?>> targetClassSet = entry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }


}
