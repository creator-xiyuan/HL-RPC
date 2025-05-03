package com.hongling.rpc.rpc.proxy;

import java.lang.reflect.Proxy;

public class ProxyFactory {
    public static <T> T getProxy(Class<T> proxyClass) {
        return (T) Proxy.newProxyInstance(proxyClass.getClassLoader(),
                new Class[]{proxyClass},
                new ServiceProxyHandler());
    }
}
