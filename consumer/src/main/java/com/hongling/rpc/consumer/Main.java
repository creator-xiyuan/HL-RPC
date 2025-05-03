package com.hongling.rpc.consumer;

import com.hongling.rpc.common.User;
import com.hongling.rpc.common.UserService;
import com.hongling.rpc.rpc.proxy.ProxyFactory;

public class Main {

    public static void main(String[] args) {
        UserService userService = ProxyFactory.getProxy(UserService.class);
        User newUser = userService.createUser("hongling");
        System.out.println("创建用户成功，用户昵称：" + newUser.getUserName());
    }
}
