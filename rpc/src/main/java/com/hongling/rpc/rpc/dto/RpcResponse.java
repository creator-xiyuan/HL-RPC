package com.hongling.rpc.rpc.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcResponse implements Serializable {

    private Object data;

    private Class<?> dataType;

    private String message;

    private Exception exception;
}
