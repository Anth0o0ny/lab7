package commands;

import interaction.Request;
import interaction.Response;
import server.ServerReceiver;
import sub.CommandsEnum;
import sub.StringConstants;

import java.util.Optional;

public class Clear extends ServerCommand{
public Clear(ServerReceiver serverReceiver){
    super(serverReceiver);
}

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.clear());
    }

    @Override
    public String getHelp() {
        return CommandsEnum.CLEAR.commandName + " : " + StringConstants.Commands.CLEAR_HELP;
    }
}
