package com.zeus.rpc.core.server.netty;


import com.zeus.rpc.core.dto.RpcRequest;
import com.zeus.rpc.core.dto.RpcResponse;
import com.zeus.rpc.core.enums.SerializerEnum;
import com.zeus.rpc.core.serializer.Serializer;
import com.zeus.rpc.core.server.IServer;
import com.zeus.rpc.core.server.netty.codec.NettyDecoder;
import com.zeus.rpc.core.server.netty.codec.NettyEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author liuwei
 * @version 2017/8/24 上午12:56
 * @modified
 * @description NettyServer
 * @see
 * @since 1.0
 */
public class NettyServer implements IServer {

    private Thread thread;

    @Override
    public void start(int port, Serializer serializer) throws Exception {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                EventLoopGroup bossGroup = new NioEventLoopGroup();
                EventLoopGroup workerGroup = new NioEventLoopGroup();
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel channel) throws Exception {
                                channel.pipeline()
                                        .addLast(new NettyEncoder(serializer, RpcResponse.class))
                                        .addLast(new NettyDecoder(serializer, RpcRequest.class))
                                        .addLast(new NettyServerHandler());

                            }
                        })
                        .option(ChannelOption.SO_TIMEOUT, 60)
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .option(ChannelOption.SO_REUSEADDR, true)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);
                try {
                    ChannelFuture channelFuture = bootstrap.bind(port).sync();
                    System.out.println("zeus-rpc netty server start success!");
                    channelFuture.channel().closeFuture().sync().channel();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    workerGroup.shutdownGracefully();
                    bossGroup.shutdownGracefully();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void stop() throws Exception {
        thread.interrupt();
        System.out.println("zeus-rpc netty server stop success!");
    }

    public static void main(String[] args) throws Exception {
        NettyServer server = new NettyServer();
        server.start(9090, SerializerEnum.HESSIAN.serializer);

        Thread.sleep(3000);

        server.stop();
    }
}
