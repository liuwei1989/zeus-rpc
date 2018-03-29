package com.zeus.rpc.core.client;


import com.zeus.rpc.core.dto.RpcRequest;
import com.zeus.rpc.core.dto.RpcResponse;
import com.zeus.rpc.core.serializer.Serializer;

/**
 * @author liuwei
 * @version 2017/8/25 上午12:54
 * @description 客户端启动类
 * @see
 * @since 1.0
 */
public abstract class IClient {

    /**
     * 服务地址
     */
    protected String serverAddress;
    /**
     * 序列化器
     */
    protected Serializer serializer;
    /**
     * 超时时间
     */
    protected long timeoutMillis;

    /**
     * 初始化
     *
     * @param serverAddress
     * @param serializer
     * @param timeoutMillis
     */
    public void init(String serverAddress, Serializer serializer, long timeoutMillis) {
        this.serverAddress = serverAddress;
        this.serializer = serializer;
        this.timeoutMillis = timeoutMillis;
    }

    /**
     * 执行器
     *
     * @param rpcRequest
     * @return
     * @throws Exception
     */
    public abstract RpcResponse invoke(RpcRequest rpcRequest) throws Exception;
}
