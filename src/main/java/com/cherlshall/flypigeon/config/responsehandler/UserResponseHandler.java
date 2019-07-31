package com.cherlshall.flypigeon.config.responsehandler;

/**
 * @author hu.tengfei
 * @since 2019/7/31
 */
@FunctionalInterface
public interface UserResponseHandler {
    String handle(String response);
}
