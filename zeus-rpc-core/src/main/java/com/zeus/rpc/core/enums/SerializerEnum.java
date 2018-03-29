package com.zeus.rpc.core.enums;


import com.zeus.rpc.core.serializer.Serializer;
import com.zeus.rpc.core.serializer.impl.HessianSerializer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuwei
 * @version 2017/8/24 上午12:34
 * @modified
 * @description 序列化枚举
 * @see
 * @since 1.0
 */
public enum SerializerEnum {
    HESSIAN(new HessianSerializer());

    public Serializer serializer;

    SerializerEnum(Serializer serializer) {
        this.serializer = serializer;
    }

    public static SerializerEnum match(String name) {
        return match(name, null);
    }

    public static SerializerEnum match(String name, SerializerEnum defaultSerializerEnum) {
        for (SerializerEnum serializerEnum : SerializerEnum.values()) {
            if (serializerEnum.name().equalsIgnoreCase(name)) {
                return serializerEnum;
            }
        }
        return defaultSerializerEnum;
    }

    public static void main(String[] args) {
        Serializer serializer = SerializerEnum.match("hessian").serializer;
        System.out.println(serializer);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("name", "张三");
        data.put("age", 28);
        data.put("birthday", new Date());
        System.out.println(serializer.deserialize(serializer.serialize(data), HashMap.class));
    }
}
