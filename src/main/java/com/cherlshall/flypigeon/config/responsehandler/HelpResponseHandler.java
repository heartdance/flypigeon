package com.cherlshall.flypigeon.config.responsehandler;

import com.cherlshall.flypigeon.register.command.CommandTree;

/**
 * @author hu.tengfei
 * @since 2019/8/1
 */
@FunctionalInterface
public interface HelpResponseHandler {
    String handle(String command, CommandTree tree);
}
