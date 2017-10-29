package com.warframe.smart4j.helper;

import com.warframe.smart4j.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/14 16:24
 * 获取所有被smart框架管理的Bean(Controller，Service等)类，，此时需要调用ClassHelper类的getBeanClassSet,
 * 然后循环调用ReflectionUtil的newInstance方法，根据类来实例化对象，最后将每次创建的对象存放到一个静态的Map<Class<?>,Object>中
 */
public final class BeanHelper {

    //定义Bean映射(用于存放Bean类与Bean实例的映射关系)
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    //进行初始化
    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class beanClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(beanClass);
            //如果有代理类代理了Controller注解的类，那么之前BEAN_MAP中这个类的实例会覆盖成新的代理对象实例
            BEAN_MAP.put(beanClass, obj);
        }
    }


    /**
     * 获取Bean映射
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取Bean实例
     */
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("cannot get bean by class:" + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }

    /**
     * 添加bean实例
     * @param cls
     * @param obj
     * 在AOPHelper中需要获取所有目标类以及拦截的切面类实例，并通过ProxyManager#createProxy方法来创建代理对象
     * 最后将其放入BEAN_MAP中
     * 所以需要在BeanHelper中新增setBean方法
     */
    public static void setBean(Class<?> cls,Object obj){
        BEAN_MAP.put(cls,obj);
    }



}
