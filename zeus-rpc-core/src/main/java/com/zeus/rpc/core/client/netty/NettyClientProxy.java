package com.zeus.rpc.core.client.netty;

import com.zeus.rpc.core.dto.RpcRequest;
import com.zeus.rpc.core.dto.RpcResponse;
import com.zeus.rpc.core.serializer.Serializer;
import com.zeus.rpc.core.server.netty.codec.NettyDecoder;
import com.zeus.rpc.core.server.netty.codec.NettyEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author liuwei
 * @version 2017/8/25 上午1:30
 * @description
 * @see
 * @since 1.0
 */
public class NettyClientProxy {

    private Channel channel;

    public void createProxy(String host, int port, final Serializer serializer) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                                .addLast(new NettyEncoder(serializer, RpcRequest.class))
                                .addLast(new NettyDecoder(serializer, RpcResponse.class))
                                .addLast(new NettyClientHandler());
                    }
                })
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_KEEPALIVE, true);
        this.channel = bootstrap.connect(host, port).sync().channel();
    }

    public Channel getChannel() {
        return this.channel;
    }

    public boolean isValidate() {
        if (this.channel != null) {
            return this.channel.isActive();
        }
        return false;
    }

    public void close() {
        if (this.channel != null) {
            if (this.channel.isOpen()) {
                this.channel.close();
            }
        }
    }

    public void invoke(RpcRequest request) throws Exception {
        this.channel.writeAndFlush(request).sync();
    }
}
