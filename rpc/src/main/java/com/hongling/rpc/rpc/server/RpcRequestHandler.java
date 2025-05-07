package com.hongling.rpc.rpc.server;

import com.hongling.rpc.rpc.RpcApplication;
import com.hongling.rpc.rpc.dto.RpcRequest;
import com.hongling.rpc.rpc.dto.RpcResponse;
import com.hongling.rpc.rpc.register.ServiceRegister;
import com.hongling.rpc.rpc.serializer.SerializerFactory;
import com.hongling.rpc.rpc.serializer.impl.JdkSerializer;
import com.hongling.rpc.rpc.serializer.Serializer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.Method;

public class RpcRequestHandler implements Handler<HttpServerRequest> {
    @Override
    public void handle(HttpServerRequest request) {
        // 实现动态配置：定义一个 序列化器名称 => 序列化器实现类对象 的 Map，然后根据名称从 Map 中获取对象（名称写在配置文件中）
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        System.out.println("Received request: " + request.method() + " " + request.uri());

        request.bodyHandler(body -> {
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            RpcResponse rpcResponse = new RpcResponse();
            if (rpcRequest == null) {
                rpcResponse.setMessage("rpcRequest is null");
                doResponse(request, rpcResponse, serializer);
                return;
            }

            try {
                Class<?> implClass = ServiceRegister.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.newInstance(), rpcRequest.getParameters());

                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("success");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }
            doResponse(request, rpcResponse, serializer);
        });
    }

    private void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer) {
        HttpServerResponse response = request.response().putHeader("content-type", "application/json");
        try {
            byte[] serialized = serializer.serialize(rpcResponse);
            response.end(Buffer.buffer(serialized));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            response.end(Buffer.buffer());
        }
    }
}
