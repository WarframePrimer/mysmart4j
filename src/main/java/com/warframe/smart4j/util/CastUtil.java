package com.warframe.smart4j.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/9/17 9:53
 * 类型转换工具类
 */
public final class CastUtil {

    /**
     * 转为String类
     */
    public static String castString(Object obj) {
        return CastUtil.castString(obj, "");
    }

    public static String castString(Object obj, String defaultVale) {
        return null != obj ? String.valueOf(obj) : defaultVale;
    }

    /**
     * 转为double类型
     */
    public static double castDouble(Object obj) {
        return CastUtil.castDouble(obj, 0);
    }

    public static double castDouble(Object obj, double defaultValue) {
        double doubleValue = defaultValue;
        if (obj != null) {
            String strValue=castString(obj);
            if(StringUtils.isNotEmpty(strValue)){
                doubleValue = Double.parseDouble(strValue);
            }
        }
        return doubleValue;
    }

    /**
     * 转为long类型
     */
    public static long castLong(Object obj) {
        return CastUtil.castLong(obj, 0);
    }

    public static long castLong(Object obj, long defaultValue) {
        long longValue = defaultValue;
        if (obj != null) {
            String strValue=castString(obj);
            if(StringUtils.isNotEmpty(strValue)){
                longValue = Long.parseLong(strValue);
            }
        }
        return longValue;
    }

    /**
     * 转为int类型
     */
    public static int castInt(Object obj) {
        return CastUtil.castInt(obj, 0);
    }

    public static int castInt(Object obj, int defaultValue) {
        int intValue = defaultValue;
        if (obj != null) {
            String strValue=castString(obj);
            if(StringUtils.isNotEmpty(strValue)){
                intValue = Integer.parseInt(strValue);
            }
        }
        return intValue;
    }


    /**
     * 转为int类型
     */
    public static boolean castBoolean(Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj != null) {
            booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return booleanValue;
    }



}
