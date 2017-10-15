package com.warframe.smart4j.bean;

import com.warframe.smart4j.util.CastUtil;

import java.util.Map;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/15 16:27
 *
 * 请求参数对象
 */
public class Param {
    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 根据参数名获取long型参数值
     */
    public long getLong(String name){
        return CastUtil.castLong(paramMap.get(name));
    }

    /**
     * 获取所有字段信息
     */
    public Map<String,Object> getMap(){
        return paramMap;
    }



}
