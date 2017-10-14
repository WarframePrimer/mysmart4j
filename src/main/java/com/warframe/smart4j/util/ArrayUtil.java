package com.warframe.smart4j.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/14 17:01
 *
 * 数组工具类 实际上是对数组的一些常用方法的封装
 */
public final class ArrayUtil {

    /**
     * 判断数组是否非空
     */
    public static boolean isNotEmpty(Object[] array){
        return ArrayUtils.isNotEmpty(array);
    }

    /**
     * 判断数组是否为空
     */
    public static boolean isEmpty(Object[] array){
        return ArrayUtils.isEmpty(array);
    }

}
