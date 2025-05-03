package com.hongling.rpc.rpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.hongling.rpc.rpc.config.RpcConfig;
import com.hongling.rpc.rpc.constant.RpcConstant;
import com.hongling.rpc.rpc.dto.RpcRequest;
import com.hongling.rpc.rpc.dto.RpcResponse;
import com.hongling.rpc.rpc.serializer.JdkSerializer;
import com.hongling.rpc.rpc.serializer.Serializer;
import com.hongling.rpc.rpc.utils.ConfigUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ServiceProxyHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        final Serializer serializer = new JdkSerializer();

        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .targetClass(method.getDeclaringClass())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .parameters(args)
                .build();

        byte[] bodyBytes = serializer.serialize(rpcRequest);
        try(HttpResponse response = HttpRequest.post("http://" + rpcConfig.getServerHost() + ":" + rpcConfig.getServerPort())
                .body(bodyBytes)
                .execute()) {
            byte[] result = response.bodyBytes();
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return rpcResponse.getData();
        }
    }
}
