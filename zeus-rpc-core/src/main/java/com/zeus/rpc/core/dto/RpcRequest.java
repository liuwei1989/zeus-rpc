package com.zeus.rpc.core.dto;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author liuwei
 * @version 2017/8/24 上午12:59
 * @modified
 * @description RpcRequest
 * @see
 * @since 1.0
 */
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = -1156748575636068067L;

    /**
     * 请求唯一id
     */
    private String requestId;
    /**
     * 请求创建时间
     */
    private Long createMillisTime;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;
    /**
     * 参数
     */
    private Object[] parameters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Long getCreateMillisTime() {
        return createMillisTime;
    }

    public void setCreateMillisTime(Long createMillisTime) {
        this.createMillisTime = createMillisTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "requestId='" + requestId + '\'' +
                ", createMillisTime=" + createMillisTime +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
