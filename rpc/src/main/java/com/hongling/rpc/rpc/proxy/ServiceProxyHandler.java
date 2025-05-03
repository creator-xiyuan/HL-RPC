package com.hongling.rpc.rpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.hongling.rpc.rpc.dto.RpcRequest;
import com.hongling.rpc.rpc.dto.RpcResponse;
import com.hongling.rpc.rpc.serializer.JdkSerializer;
import com.hongling.rpc.rpc.serializer.Serializer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ServiceProxyHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final Serializer serializer = new JdkSerializer();

        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .targetClass(method.getDeclaringClass())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .parameters(args)
                .build();

        byte[] bodyBytes = serializer.serialize(rpcRequest);
        try(HttpResponse response = HttpRequest.post("http://localhost:8080")
                .body(bodyBytes)
                .execute()) {
            byte[] result = response.bodyBytes();
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return rpcResponse.getData();
        }
    }
}
