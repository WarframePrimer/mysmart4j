package com.warframe.smart4j.proxy;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/18 17:00
 *
 * 代理接口
 */
public interface Proxy {

    /**
     * 执行链式代理
     * 链式代理：可以将多个代理通过一个链子串起来，一个个的去执行，执行顺序取决于添加到链上的先后顺序。
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
