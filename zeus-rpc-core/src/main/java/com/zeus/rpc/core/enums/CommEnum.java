package com.zeus.rpc.core.enums;

import com.zeus.rpc.core.client.IClient;
import com.zeus.rpc.core.client.netty.NettyClient;
import com.zeus.rpc.core.server.IServer;
import com.zeus.rpc.core.server.netty.NettyServer;

/**
 * @author liuwei
 * @version 2017/8/25 上午12:32
 * @description 通信配置枚举
 * @see
 * @since 1.0
 */
public enum CommEnum {
    NETTY(NettyServer.class, NettyClient.class);

    public final Class<? extends IServer> serverClass;
    public final Class<? extends IClient> clientClass;

    private CommEnum(Class<? extends IServer> serverClass, Class<? extends IClient> clientClass) {
        this.serverClass = serverClass;
        this.clientClass = clientClass;
    }
}
