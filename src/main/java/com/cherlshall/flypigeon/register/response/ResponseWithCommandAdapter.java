package com.cherlshall.flypigeon.register.response;

import com.cherlshall.flypigeon.register.RegisterCenter;

/**
 * 将 {@link ResponseWithCommandAdapter} 适配为 {@link ResponseWithCommand}
 *
 * @see RegisterCenter
 * @author hu.tengfei
 * @since 2019/7/30
 */
public class ResponseWithCommandAdapter implements ResponseWithCommand {

    private ResponseWithCommand responseWithCommand;

    public ResponseWithCommandAdapter(ResponseWithoutCommand responseWithoutCommand) {
        this.responseWithCommand = command -> responseWithoutCommand.getResponse();
    }

    @Override
    public String getResponse(String command) {
        return responseWithCommand.getResponse(command);
    }
}
