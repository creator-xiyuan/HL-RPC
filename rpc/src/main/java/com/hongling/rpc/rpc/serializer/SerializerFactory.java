package com.hongling.rpc.rpc.serializer;

import com.hongling.rpc.rpc.serializer.impl.JdkSerializer;
import com.hongling.rpc.rpc.spi.SpiLoader;


/**
 * 序列化器工厂（用于获取序列化器对象）
 */
public class SerializerFactory {

    static {
        // 工厂初始化时加载所有序列化器
        SpiLoader.load(Serializer.class);
    }

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Serializer getInstance(String key) {
        return SpiLoader.getInstance(Serializer.class, key);
    }

}
