package com.warframe.smart4j.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/18 17:03
 * <p>
 * 代理链
 *
 * 在ProxyChain中定义了一系列的变量
 * targetClass 目标类
 * targetObject 目标对象
 * targetMethod 目标方法
 * methodProxy 方法代理
 * methodParams 方法参数
 * 此外还包括proxyList(代理列表)
 * proxyIndex(代理索引)
 *
 *
 */
public class ProxyChain {

    private final Class<?> targetClass;
    private final Object targetObject;
    private final Method targetMethod;
    //MethodProxy这个类是CGlib开源项目为我们提供的一个方法代理对象，在doProxyChain方法中被使用
    private final MethodProxy methodProxy;
    private final Object[] methodParams;

    private List<Proxy> proxyList = new ArrayList<>();
    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams,List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }


    //在该方法中，通过proxyIndex来充当对象的计数器，若未达到proxyList的上限，则取出对应的Proxy对象，并调用其doProxy方法
    //在Proxy接口的实现中会提供相应的横切逻辑，并调用doProxyChain方法，随后将再次调用当前ProxyChain对象的doProxyChain方法
    //知道proxyIndex达到proxyList的上限，最后调用methodProxy的invokeSuper方法，执行目标对象的业务逻辑。
    public Object doProxyChain() throws Throwable {
        Object methodResult;

        if (proxyIndex < proxyList.size()) {
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            methodResult = methodProxy.invokeSuper(targetObject,methodParams);
        }

        return methodResult;
    }
}
