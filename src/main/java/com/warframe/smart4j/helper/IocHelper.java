package com.warframe.smart4j.helper;

import com.warframe.smart4j.util.CollectionUtil;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;


/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/14 16:40
 * <p>
 * Q:Controller中定义Service成员变量，然后再Controller的Action方法中调用Service成员变量的方法，要怎么实例化Service成员变量呢？
 * A:最简单的方式是，先通过BeanHelper获取所有BeanMap。然后遍历整个映射关系，分别取出Bean类与Bean实例，
 * 进而通过反射获取所有的成员变量。继续遍历这些成员变量，在循环中判断当前成员变量是够带有Inject注解，若带有注解，则从BeanMap中根据Bean类取出实例，然后通过
 * ReflectionUtil的setField方法修改当前成员变量的值
 * <p>
 * 实现依赖注入
 */
public class IocHelper {

    static {
        //获取所有的Bean类和Bean实例之间的映射关系
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            //遍历beanMap
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                //从beanMap中获取Bean类和实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                //获取Bean类定义的所有成员变量
                Field[] beanFields = beanClass.getDeclaredFields();
                //TODO
            }
        }
    }

}
