package com.zeus.rpc.core.server.netty.codec;

import com.zeus.rpc.core.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author liuwei
 * @version 2018/3/29 下午4:21
 * @description Netty编码器
 * @see
 * @since 1.0
 */
public class NettyEncoder extends MessageToByteEncoder<Object> {

    private Serializer serializer;
    private Class<?> clazz;

    public NettyEncoder(Serializer serializer, Class<?> clazz) {
        this.serializer = serializer;
        this.clazz = clazz;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object in, ByteBuf out) throws Exception {
        if (clazz.isInstance(in)) {
            byte[] bytes = serializer.serialize(in);
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        }
    }
}
