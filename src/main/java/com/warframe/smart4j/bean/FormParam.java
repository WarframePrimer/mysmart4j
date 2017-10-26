package com.warframe.smart4j.bean;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/26 9:27
 *
 * 封装表单参数
 */
public class FormParam {

    private String fieldName;
    private Object fieldValue;


    public FormParam(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
