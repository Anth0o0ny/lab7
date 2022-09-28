package commands;

import interaction.Request;
import interaction.Response;
import server.ServerReceiver;
import sub.AbstractCommand;

public abstract class ServerCommand extends AbstractCommand<Request, Response> {
    protected ServerReceiver serverReceiver;

    public ServerCommand(ServerReceiver serverReceiver) {
        this.serverReceiver = serverReceiver;
    }
}

