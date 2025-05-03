package com.hongling.rpc.provider;

import com.hongling.rpc.common.UserService;
import com.hongling.rpc.rpc.register.ServiceRegister;
import com.hongling.rpc.rpc.server.RpcRequestHandler;
import io.vertx.core.Vertx;


public class Main {

    public static void main(String[] args) {
        ServiceRegister.register(UserService.class.getName(), UserServiceImpl.class);

        // todo 这里是否要划到 rpc 模块？
        final int port = 8080;
        Vertx.vertx()
            .createHttpServer()
            .requestHandler(new RpcRequestHandler())
            .listen(port, result -> {
                if (result.succeeded()) {
                    System.out.println("Server is now listening on port " + port);
                } else {
                    System.out.println("Failed to start server: " + result.cause());
                }
            });
    }
}
