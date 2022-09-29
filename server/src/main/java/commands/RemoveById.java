package commands;

import interaction.Request;
import interaction.Response;  
import server.ServerReceiver;
import sub.CommandsEnum;
import sub.StringConstants;

import java.util.Optional;

public class RemoveById extends ServerCommand{
    public RemoveById(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.removeById(arg.getArgument(), arg.getLogin()));
    }

    @Override
    public String getHelp() {
        return CommandsEnum.REMOVE_BY_ID.commandName + " id : " + StringConstants.Commands.REMOVE_BY_ID_HELP;
    }
}
