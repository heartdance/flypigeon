package com.cherlshall.flypigeon.exception;

/**
 * @author hu.tengfei
 * @since 2019/7/30
 */
public class CommandRegisterException extends RuntimeException {

    public CommandRegisterException() {
        super();
    }

    public CommandRegisterException(String msg) {
        super(msg);
    }
}
