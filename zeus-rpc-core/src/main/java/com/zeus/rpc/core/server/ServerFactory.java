package com.zeus.rpc.core.server;


import com.zeus.rpc.core.annotiations.RpcService;
import com.zeus.rpc.core.dto.RpcRequest;
import com.zeus.rpc.core.dto.RpcResponse;
import com.zeus.rpc.core.enums.CommEnum;
import com.zeus.rpc.core.enums.SerializerEnum;
import com.zeus.rpc.core.serializer.Serializer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuwei
 * @version 2017/8/25 上午12:27
 * @description
 * @see
 * @since 1.0
 */
public class ServerFactory implements ApplicationContextAware, InitializingBean, DisposableBean {

    private int port = 9090;
    private CommEnum comEnum = CommEnum.NETTY;
    private Serializer serializer = SerializerEnum.HESSIAN.serializer;

    private IServer server;

    private static Map<String, Object> serviceMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (serviceMap != null && !serviceMap.isEmpty()) {
            for (Object serviceBean : serviceMap.values()) {
                String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
                serviceMap.put(interfaceName, serviceBean);
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        this.server.stop();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.server = comEnum.serverClass.newInstance();
        this.server.start(port, serializer);
    }

    public static RpcResponse invoke(RpcRequest rpcRequest, Object serviceBean) {
        if (serviceBean == null) {
            serviceBean = serviceMap.get(rpcRequest.getClassName());
        }
        if (serviceBean == null) {
            return null;
        }
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(rpcRequest.getRequestId());

        Class<?> serviceClass = serviceBean.getClass();
        String methodName = rpcRequest.getMethodName();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] parameters = rpcRequest.getParameters();

        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod fastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        try {
            Object result = fastMethod.invoke(serviceBean, parameters);
            rpcResponse.setResult(result);
        } catch (Throwable t) {
            t.printStackTrace();
            rpcResponse.setThrowable(t);
        }
        return rpcResponse;
    }
}
