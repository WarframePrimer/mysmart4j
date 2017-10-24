package com.warframe.smart4j.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.util.List;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/18 19:33
 *
 * 代码管理器
 * 提供创建代理对象的方法
 * 输入一个目标类和一组Proxy接口实现，输出一个代理对象
 *
 * 使用CGLib提供的Enhancer#create方法创建代理对象，将intercept的参数传入ProxyChain的构造器
 */
public class ProxyManager {


    //生成代理类

    /**
     * 生成代理类
     * @param targetClass
     * @param proxyList
     * @param <T>
     * @return
     */
    public static <T> T createProxy(final Class<?> targetClass,final List<Proxy> proxyList){

        return (T) Enhancer.create(targetClass, (MethodInterceptor) (o, method, objects, methodProxy) ->
                //将intercept的参数传入ProxyChain的构造器中
                new ProxyChain(targetClass,o,method,methodProxy,objects,proxyList).doProxyChain());
    }

}
