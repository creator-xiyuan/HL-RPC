package com.hongling.rpc.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String userName;

    public User(String userName) {
        this.userName = userName;
    }
}
