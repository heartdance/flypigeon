package com.cherlshall.flypigeon.config.responsehandler;

import java.util.Set;

/**
 * @author hu.tengfei
 * @since 2019/7/31
 */
@FunctionalInterface
public interface CommandResponseHandler {
    String handle(Set<String> trunks, Set<String> leaves);
}
