package com.cherlshall.flypigeon.register.command;

/**
 * @author hu.tengfei
 * @since 2019/7/30
 */
public class CommandTrunk extends CommandTree {
    private final String name;

    public CommandTrunk(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
