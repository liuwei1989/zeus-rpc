package com.zeus.rpc.core.annotiations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liuwei
 * @version 2017/8/25 上午12:37
 * @description RpcService
 * @see
 * @since 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcService {

    Class<?> value();
}
