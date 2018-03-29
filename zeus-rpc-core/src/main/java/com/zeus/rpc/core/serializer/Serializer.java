package com.zeus.rpc.core.serializer;

/**
 * @author liuwei
 * @version 2018/3/29 下午3:34
 * @description 序列化器
 * @see
 * @since 1.0
 */
public interface Serializer {
    /**
     * 序列化
     *
     * @param obj
     * @param <T>
     * @return
     */
    <T> byte[] serialize(T obj);

    /**
     * 反序列化
     *
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    <T> Object deserialize(byte[] bytes, Class<T> clazz);
}
