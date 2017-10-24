package com.warframe.smart4j;

import com.warframe.smart4j.helper.*;
import com.warframe.smart4j.util.ClassUtil;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/15 15:07
 * 之前创建了BeanHelper,ClassHelper,ControllerHelper,IocHelper,这是个Helper类需要通过一个入口程序来加载它们
 * 实际上是记载它们的静态代码块。
 * <p>
 * <p>
 * 加载相应的Helper类
 */
public class HelperLoader {

    public static void init() {
        //AopHelper需要放在IocHelper之前进行加载，因为首先需要通过AopHelper获取代理对象，然后才能通过IocHelper进行依赖注入
        Class<?>[] classList = {ClassHelper.class, BeanHelper.class, AopHelper.class, IocHelper.class, ControllerHelper.class};

        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}
