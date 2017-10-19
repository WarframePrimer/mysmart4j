package com.warframe.smart4j.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/18 20:30
 * <p>
 * 切面代理
 * 抽象类
 */
public abstract class AspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    //实现Proxy接口提供的doProxy方法
    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("proxy failure", e);
            error(cls,method,params,e);
            throw e;
        }finally {
            end();
        }

        return result;
    }


    public void begin() {

    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
        return true;
    }

    public void end() {
    }

    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {

    }

    public void after(Class<?> cls, Method method, Object[] params) throws Throwable {

    }

    public void end(Class<?> cls, Method method, Object[] params) throws Throwable {

    }

    public void error(Class<?> cls, Method method, Object[] params,Throwable e){

    }


}


