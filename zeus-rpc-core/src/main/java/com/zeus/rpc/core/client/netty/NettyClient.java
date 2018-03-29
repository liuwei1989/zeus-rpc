package com.zeus.rpc.core.client.netty;


import com.zeus.rpc.core.client.IClient;
import com.zeus.rpc.core.dto.RpcCallbackFuture;
import com.zeus.rpc.core.dto.RpcRequest;
import com.zeus.rpc.core.dto.RpcResponse;
import com.zeus.rpc.core.enums.SerializerEnum;

/**
 * @author liuwei
 * @version 2017/8/25 上午1:23
 * @description netty客户端
 * @see
 * @since 1.0
 */
public class NettyClient extends IClient {
    @Override
    public RpcResponse invoke(RpcRequest rpcRequest) throws Exception {
        try {
            RpcCallbackFuture rpcCallbackFuture = new RpcCallbackFuture(rpcRequest);
            NettyClientProxy proxy = new NettyClientProxy();
            proxy.createProxy("localhost", 9090, SerializerEnum.HESSIAN.serializer);
            proxy.invoke(rpcRequest);
            return rpcCallbackFuture.get(this.timeoutMillis);
        } finally {
            RpcCallbackFuture.futurePool.remove(rpcRequest.getRequestId());
        }
    }
}
