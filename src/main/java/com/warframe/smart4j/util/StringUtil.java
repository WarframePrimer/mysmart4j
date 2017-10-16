package com.warframe.smart4j.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/16 15:22
 *
 * 字符串处理工具类
 */
public final class StringUtil {

    public static boolean isEmpty(String str){
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str){
        return StringUtils.isNotEmpty(str);
    }

    public static String[] splitString(String source,String pattern){
        return StringUtils.split(source,pattern);
    }

}
