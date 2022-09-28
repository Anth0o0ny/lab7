package commands;

import interaction.Request;
import interaction.Response;
import server.ServerReceiver;
import sub.CommandsEnum;
import sub.StringConstants;

import java.util.Optional;

public class InsertAt extends ServerCommand{
    public InsertAt(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.insertAt(arg.getArgument(), arg.getMovie()));
    }

    @Override
    public String getHelp() {
        return CommandsEnum.INSERT_AT.commandName + " index : " + StringConstants.Commands.INSERT_AT_HELP;
    }
}
