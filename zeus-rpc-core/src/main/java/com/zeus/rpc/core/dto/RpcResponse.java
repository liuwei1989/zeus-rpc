package com.zeus.rpc.core.dto;

import java.io.Serializable;

/**
 * @author liuwei
 * @version 2017/8/24 上午1:03
 * @description RpcResponse
 * @see
 * @since 1.0
 */
public class RpcResponse implements Serializable {
    private static final long serialVersionUID = -6851967183635378110L;

    /**
     * 请求唯一id
     */
    private String requestId;
    /**
     * 异常信息
     */
    private Throwable throwable;
    /**
     * 响应结果
     */
    private Object result;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
