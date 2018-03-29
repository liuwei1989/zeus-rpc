package com.zeus.rpc.core.dto;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuwei
 * @version 2017/8/25 上午1:17
 * @description
 * @see
 * @since 1.0
 */
public class RpcCallbackFuture {
    private RpcRequest rpcRequest;
    private RpcResponse rpcResponse;

    private Object lock = new Object();
    private volatile boolean isDone;

    public static ConcurrentHashMap<String, RpcCallbackFuture> futurePool = new ConcurrentHashMap<>();

    public RpcCallbackFuture(RpcRequest rpcRequest) {
        this.rpcRequest = rpcRequest;
        futurePool.put(rpcRequest.getRequestId(), this);
    }

    public RpcResponse getRpcResponse() {
        return rpcResponse;
    }

    public void setRpcResponse(RpcResponse rpcResponse) {
        this.rpcResponse = rpcResponse;
        synchronized (lock) {
            isDone = true;
            lock.notifyAll();
        }
    }

    public RpcResponse get(long timeout) throws InterruptedException {
        if (!isDone) {
            synchronized (lock) {
                lock.wait(timeout);
            }
        }
        if (!isDone) {
            System.out.println(">>>>>>>> request timeout!");
        }
        return rpcResponse;
    }
}
