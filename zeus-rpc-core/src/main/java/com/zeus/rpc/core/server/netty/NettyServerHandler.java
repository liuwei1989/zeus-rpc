package com.zeus.rpc.core.server.netty;

import com.zeus.rpc.core.dto.RpcRequest;
import com.zeus.rpc.core.dto.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author liuwei
 * @version 2017/8/25 上午12:23
 * @description 功能描述
 * @see
 * @since 1.0
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest rpcRequest) throws Exception {
//        RpcResponse rpcResponse = ServerFactory.invoke(rpcRequest, null);
//        ctx.writeAndFlush(rpcResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("zeus-rpc provider netty server caught exception:" + cause);
        ctx.close();
    }
}
