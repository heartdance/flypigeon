package com.cherlshall.flypigeon.service;

import com.cherlshall.flypigeon.register.RegisterCenter;

/**
 * 服务端接口，为了方便拓展服务端其他实现
 *
 * @author hu.tengfei
 * @since 2019/7/30
 */
public interface SendService {
    boolean startup(RegisterCenter registerCenter);
    boolean shutdown();
}
