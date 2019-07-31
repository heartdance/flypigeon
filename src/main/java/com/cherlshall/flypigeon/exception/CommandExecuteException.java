package com.cherlshall.flypigeon.exception;

/**
 * @author hu.tengfei
 * @since 2019/7/30
 */
public class CommandExecuteException extends RuntimeException {

    public CommandExecuteException() {
        super();
    }

    public CommandExecuteException(String msg) {
        super(msg);
    }
}
