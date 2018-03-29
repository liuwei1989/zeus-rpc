package com.zeus.rpc.core.server.netty.codec;

import com.zeus.rpc.core.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author liuwei
 * @version 2018/3/29 下午4:26
 * @description Netty解码器
 * @see
 * @since 1.0
 */
public class NettyDecoder extends ByteToMessageDecoder {
    private Serializer serializer;
    private Class<?> clazz;

    public NettyDecoder(Serializer serializer, Class<?> clazz) {
        this.serializer = serializer;
        this.clazz = clazz;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        Object object = serializer.deserialize(data, clazz);
        out.add(object);
    }
}
