package com.cherlshall.flypigeon.register.response;

/**
 * 生成响应
 * 编写响应内容时，能够获得命令信息
 * 在编写响应内容时无需提取命令信息的情况下
 * 推荐使用此函数代替 {@link ResponseWithoutCommand}
 *
 * @author hu.tengfei
 * @since 2019/7/30
 */
@FunctionalInterface
public interface ResponseWithCommand {
    String getResponse(String command);
}
