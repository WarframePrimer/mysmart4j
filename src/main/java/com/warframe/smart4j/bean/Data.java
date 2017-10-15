package com.warframe.smart4j.bean;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/15 16:36
 *Data类型的数据封装了一个Object类型的模型数据，框架会将该对象写入HttpServletRequest对象中，
 * 从而直接输出至浏览器
 *
 *
 * 返回数据对象
 */
public class Data {

    /**
     * 模型数据
     */
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
