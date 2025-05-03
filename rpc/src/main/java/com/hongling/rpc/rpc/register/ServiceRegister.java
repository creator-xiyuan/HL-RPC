package com.hongling.rpc.rpc.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务注册中心
 */
public class ServiceRegister {
    private final static Map<String, Class> serviceMap = new ConcurrentHashMap<>();

    // todo 这个方法有用吗，或者说这个 Map 有用吗
    public static Class get(String serviceName) {
        return serviceMap.get(serviceName);
    }

    public static Class register(String serviceName, Class serviceClass) {
        return serviceMap.put(serviceName, serviceClass);
    }
}
