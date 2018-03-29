package com.zeus.rpc.core.serializer.impl;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.zeus.rpc.core.serializer.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author liuwei
 * @version 2017/8/24 上午12:19
 * @modified
 * @description hessian序列化
 * @see
 * @since 1.0
 */
public class HessianSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(baos);
        byte[] bytes = null;
        try {
            ho.writeObject(obj);
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ho.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    @Override
    public <T> Object deserialize(byte[] bytes, Class<T> clazz) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        HessianInput hi = new HessianInput(bais);
        Object object = null;
        try {
            object = hi.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            hi.close();
        }
        return object;
    }
}
