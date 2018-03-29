package com.zeus.rpc.core.client;

import com.zeus.rpc.core.dto.RpcRequest;
import com.zeus.rpc.core.dto.RpcResponse;
import com.zeus.rpc.core.enums.CommEnum;
import com.zeus.rpc.core.enums.SerializerEnum;
import com.zeus.rpc.core.serializer.Serializer;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @author liuwei
 * @version 2017/8/25 上午12:58
 * @description
 * @see
 * @since 1.0
 */
public class ClientFactory implements FactoryBean<Object>, InitializingBean {
    private String serverAddress = "localhost:9090";
    private CommEnum comEnum = CommEnum.NETTY;
    private Serializer serializer = SerializerEnum.HESSIAN.serializer;
    private Class<?> iface;
    private long timeoutMillis = 5000;

    IClient client = null;

    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(Thread.currentThread()
                        .getContextClassLoader(), new Class[]{iface},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        RpcRequest request = new RpcRequest();
                        request.setRequestId(UUID.randomUUID().toString());
                        request.setCreateMillisTime(System.currentTimeMillis());
                        request.setClassName(method.getDeclaringClass().getName());
                        request.setMethodName(method.getName());
                        request.setParameterTypes(method.getParameterTypes());
                        request.setParameters(args);

                        RpcResponse response = client.invoke(request);
                        if (response == null) {
                            throw new Exception("rpc netty response not found.");
                        }
                        return response.getResult();
                    }
                });
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        client = comEnum.clientClass.newInstance();
        client.init(serverAddress, serializer, timeoutMillis);
    }

    public Class<?> getIface() {
        return iface;
    }

    public void setIface(Class<?> iface) {
        this.iface = iface;
    }
}
