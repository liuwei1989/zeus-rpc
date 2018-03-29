package com.zeus.rpc.core.client.netty;

import com.zeus.rpc.core.dto.RpcCallbackFuture;
import com.zeus.rpc.core.dto.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author liuwei
 * @version 2017/8/25 上午1:31
 * @see
 * @since 1.0
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse rpcResponse) throws Exception {
        RpcCallbackFuture future = RpcCallbackFuture.futurePool.get(rpcResponse.getRequestId());
        future.setRpcResponse(rpcResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("zeus-rpc provider netty client caught exception:" + cause);
        ctx.close();
    }
}
