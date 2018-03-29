package com.zeus.rpc.core.client.netty;

import com.zeus.rpc.core.serializer.Serializer;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuwei
 * @version 2017/8/25 下午2:07
 * @see
 * @since 1.0
 */
public class NettyClientPool {
    private int maxTotal = 50;
    private boolean testOnBorrow = true;

    private GenericObjectPool<NettyClientPoolFactory> pool;
    private ConcurrentHashMap<String, NettyClientPool> clientPoolMap = new ConcurrentHashMap();

    public NettyClientPool(String host, int port, Serializer serializer) {
        this.pool = new GenericObjectPool(new NettyClientPoolFactory(host, port, serializer));
        this.pool.setTestOnBorrow(testOnBorrow);
        this.pool.setMaxTotal(maxTotal);
    }

    public GenericObjectPool<NettyClientPoolFactory> getPool() {
        return pool;
    }

    public GenericObjectPool<NettyClientPoolFactory> getPool(String serverAddress, Serializer serializer) {
        if (serverAddress == null) {
            return null;
        }
        NettyClientPool clientPool = clientPoolMap.get(serverAddress);
        if (clientPool != null) {
            return clientPool.getPool();
        }
        String[] hostInfo = serverAddress.split(":");
        NettyClientPool newNettyClientPool = new NettyClientPool(hostInfo[0], Integer.parseInt(hostInfo[1]), serializer);
        clientPoolMap.put(serverAddress, newNettyClientPool);
        return newNettyClientPool.getPool();
    }

}
