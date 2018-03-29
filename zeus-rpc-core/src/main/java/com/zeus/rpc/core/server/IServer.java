package com.zeus.rpc.core.server;


import com.zeus.rpc.core.serializer.Serializer;

/**
 * @author liuwei
 * @version 2017/8/24 上午12:52
 * @modified
 * @description 服务启动类
 * @see
 * @since 1.0
 */
public interface IServer {

    /**
     * 启动
     *
     * @param port
     * @param serializer
     * @throws Exception
     */
    void start(final int port, final Serializer serializer) throws Exception;

    /**
     * 停止
     *
     * @throws Exception
     */
    void stop() throws Exception;

}
