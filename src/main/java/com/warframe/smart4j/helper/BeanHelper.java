package com.warframe.smart4j.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/14 16:24
 * 获取所有被smart框架管理的Bean(Controller，Service等)类，，此时需要调用ClassHelper类的getBeanClassSet,
 * 然后循环调用ReflectionUtil的newInstance方法，根据类来实例化对象，最后将每次创建的对象存放到一个静态的Map<Class<?>,Object>中
 *
 */
public final class BeanHelper {

    //定义Bean映射(用于存放Bean类与Bean实例的映射关系)
    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<>();



}
