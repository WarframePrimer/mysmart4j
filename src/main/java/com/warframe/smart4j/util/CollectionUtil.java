package com.warframe.smart4j.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/14 16:52
 * <p>
 * 集合工具类
 */
public final class CollectionUtil {

    /**
     * 判断集合是够为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * 判断集合是够非空
     */
    public static boolean isNotEmpty(Collection<?> collection){
        return CollectionUtils.isNotEmpty(collection);
    }

    /**
     * 判断map是否为空
     */
    public static boolean isEmpty(Map<?,?> map){
        return MapUtils.isEmpty(map);
    }

    /**
     * 判断map是够非空
     */
    public static boolean isNotEmpty(Map<?,?> map){
        return MapUtils.isNotEmpty(map);
    }

}
