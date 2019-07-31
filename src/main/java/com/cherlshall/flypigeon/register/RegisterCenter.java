package com.cherlshall.flypigeon.register;

import com.cherlshall.flypigeon.config.Configuration;
import com.cherlshall.flypigeon.exception.CommandExecuteException;
import com.cherlshall.flypigeon.exception.CommandRegisterException;
import com.cherlshall.flypigeon.register.command.CommandLeaf;
import com.cherlshall.flypigeon.register.command.CommandTree;
import com.cherlshall.flypigeon.register.command.CommandTrunk;
import com.cherlshall.flypigeon.register.response.ResponseWithCommand;
import com.cherlshall.flypigeon.register.response.ResponseWithCommandAdapter;
import com.cherlshall.flypigeon.register.response.ResponseWithoutCommand;

import java.util.Set;

/**
 * 注册中心
 * 负责命令的注册、存储、响应
 *
 * @author hu.tengfei
 * @since 2019/7/30
 */
public class RegisterCenter {

    private Configuration configuration;
    private final CommandTree commandTree = new CommandTree();

    public void register(String command, ResponseWithCommand executor) {
        String[] commandItems = splitCommand(command);
        CommandTree currentNode = this.commandTree;
        int lastIndex = commandItems.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            currentNode = currentNode.addTrunk(commandItems[i]);
        }
        currentNode.addLeaf(commandItems[lastIndex], executor);
    }

    public void register(String command, ResponseWithoutCommand executor) {
        register(command, new ResponseWithCommandAdapter(executor));
    }

    public String execute(String command) {
        try {
            String[] commandItems = splitCommand(command);
            CommandTree currentNode = this.commandTree;
            int lastIndex = commandItems.length - 1;
            for (int i = 0; i < lastIndex; i++) {
                currentNode = currentNode.getTrunk(commandItems[i]);
                if (currentNode == null) {
                    throw new CommandExecuteException("command not register, command = " + command);
                }
            }
            CommandLeaf leaf = currentNode.getLeaf(commandItems[lastIndex]);
            if (leaf == null) {
                CommandTrunk trunk = currentNode.getTrunk(commandItems[lastIndex]);
                if (trunk == null) {
                    throw new CommandExecuteException("command not register, command = " + command);
                }
                Set<String> trunkNames = trunk.getTrunkNames();
                Set<String> leafNames = trunk.getLeafNames();
                return configuration.getCommandResponseHandler().handle(trunkNames, leafNames);
            }
            return configuration.getUserResponseHandler().handle(leaf.getResponseWithCommand().getResponse(command));
        } catch (Exception e) {
            return configuration.getExceptionResponseHandler().handle(e);
        }

    }

    private String[] splitCommand(String command) {
        if (command == null) {
            throw new CommandRegisterException("command cannot null");
        }
        String commandTrim = command.trim();
        if (commandTrim.isEmpty()) {
            throw new CommandRegisterException("command cannot empty, command = " + command);
        }
        return commandTrim.split("\\s+");
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

}
