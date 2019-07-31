package com.cherlshall.flypigeon.register.response;

/**
 * 生成响应
 * 若响应内容中需要提取命令信息，需要使用 {@link ResponseWithCommand}
 * 若存在动态生成命令的情况，推荐使用 {@link ResponseWithCommand}, 以节省适配器的开销
 *
 * @author hu.tengfei
 * @since 2019/7/30
 */
@FunctionalInterface
public interface ResponseWithoutCommand {
    String getResponse();
}
