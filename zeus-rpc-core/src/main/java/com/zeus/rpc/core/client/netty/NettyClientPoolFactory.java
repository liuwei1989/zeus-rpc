package com.zeus.rpc.core.client.netty;

import com.zeus.rpc.core.serializer.Serializer;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author liuwei
 * @version 2017/8/25 下午2:01
 * @description
 * @see
 * @since 1.0
 */
public class NettyClientPoolFactory extends BasePooledObjectFactory<NettyClientProxy> {

    private String host;
    private int port;
    private Serializer serializer;

    public NettyClientPoolFactory(String host, int port, Serializer serializer) {
        this.host = host;
        this.port = port;
        this.serializer = serializer;
    }

    @Override
    public NettyClientProxy create() throws Exception {
        NettyClientProxy nettyClientProxy = new NettyClientProxy();
        nettyClientProxy.createProxy(host, port, serializer);
        return nettyClientProxy;
    }

    @Override
    public PooledObject<NettyClientProxy> wrap(NettyClientProxy nettyClientProxy) {
        return new DefaultPooledObject(nettyClientProxy);
    }

    @Override
    public boolean validateObject(PooledObject<NettyClientProxy> p) {
        NettyClientProxy nettyClientProxy = p.getObject();
        return nettyClientProxy.isValidate();
    }

    @Override
    public void destroyObject(PooledObject<NettyClientProxy> p) throws Exception {
        NettyClientProxy nettyClientProxy = p.getObject();
        nettyClientProxy.close();
    }
}
