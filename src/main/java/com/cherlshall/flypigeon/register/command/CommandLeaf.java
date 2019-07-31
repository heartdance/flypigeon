package com.cherlshall.flypigeon.register.command;

import com.cherlshall.flypigeon.register.response.ResponseWithCommand;

/**
 * @author hu.tengfei
 * @since 2019/7/30
 */
public class CommandLeaf {
    private final String name;
    private final ResponseWithCommand responseWithCommand;

    public CommandLeaf(String name, ResponseWithCommand responseWithCommand) {
        this.name = name;
        this.responseWithCommand = responseWithCommand;
    }

    public String getName() {
        return name;
    }

    public ResponseWithCommand getResponseWithCommand() {
        return responseWithCommand;
    }
}
