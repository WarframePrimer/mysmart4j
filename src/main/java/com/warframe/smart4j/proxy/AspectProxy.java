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
 *
 * ProxyManager是用来创建代理对象的，需要由切面类来调用ProxyManager，因为在切面类中，需要在目标方法被调用的前后增加相应的逻辑。
 * 所以需要提供一个抽象类，让它提供一个模板方法，并在该抽象类的具体实现中扩展该模板方法。
 * 所以就有了AspectProxy这个类
 */
public abstract class AspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    //实现Proxy接口提供的doProxy方法

    /**
     * doProxy方法，从ProxyChain参数中获取targetClass，targetMethod和methodParams,
     * 随后通过try...catch...finally代码块来实现调用框架，从框架中抽象出一系列的hook，这些抽象方法可在
     * AspectProxy的子类中有选择的进行实现
     * @param proxyChain
     * @return
     * @throws Throwable
     */
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


    public void error(Class<?> cls, Method method, Object[] params,Throwable e){

    }


}


