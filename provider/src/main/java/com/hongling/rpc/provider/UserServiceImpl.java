package com.hongling.rpc.provider;

import com.hongling.rpc.common.User;
import com.hongling.rpc.common.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User createUser(String username) {
        return new User(username);
    }
}
