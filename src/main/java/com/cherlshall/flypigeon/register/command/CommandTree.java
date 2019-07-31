package com.cherlshall.flypigeon.register.command;

import com.cherlshall.flypigeon.exception.CommandRegisterException;
import com.cherlshall.flypigeon.register.response.ResponseWithCommand;

import java.util.*;

/**
 * @author hu.tengfei
 * @since 2019/7/30
 */
public class CommandTree {

    private final Map<String, CommandTrunk> trunks;
    private final Map<String, CommandLeaf> leaves;

    public CommandTree() {
        trunks = new TreeMap<>();
        leaves = new TreeMap<>();
    }

    public CommandTrunk addTrunk(String name) {
        if (leaves.containsKey(name)) {
            throw new CommandRegisterException(
                    "cannot add this trunk, because already contain same name leaf, name = " + name);
        }
        CommandTrunk trunk;
        if ((trunk = trunks.get(name)) != null) {
            return trunk;
        }
        trunk = new CommandTrunk(name);
        trunks.put(name, trunk);
        return trunk;
    }

    public CommandLeaf addLeaf(String name, ResponseWithCommand responseWithCommand) {
        if (trunks.containsKey(name)) {
            throw new CommandRegisterException(
                    "cannot add this leaf, because already contain same name trunk, name = " + name);
        }
        CommandLeaf leaf;
        if ((leaf = leaves.get(name)) != null) {
            return leaf;
        }
        leaf = new CommandLeaf(name, responseWithCommand);
        leaves.put(name, leaf);
        return leaf;
    }

    public CommandTrunk getTrunk(String name) {
        return trunks.get(name);
    }

    public CommandLeaf getLeaf(String name) {
        return leaves.get(name);
    }

    public Map<String, CommandTrunk> getTrunks() {
        return trunks;
    }

    public Map<String, CommandLeaf> getLeaves() {
        return leaves;
    }

    public Set<String> getTrunkNames() {
        return trunks.keySet();
    }

    public Set<String> getLeafNames() {
        return leaves.keySet();
    }

}
