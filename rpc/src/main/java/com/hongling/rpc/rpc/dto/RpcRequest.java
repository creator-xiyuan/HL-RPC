package com.hongling.rpc.rpc.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RpcRequest implements Serializable {

    private String serviceName;

    private Class<?> targetClass;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] parameters;
}
