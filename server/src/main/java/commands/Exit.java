package commands;

import interaction.Request;
import interaction.Response;
import server.ServerReceiver;
import sub.CommandsEnum;
import sub.StringConstants;

import java.util.Optional;

public class Exit extends ServerCommand{
    public Exit(ServerReceiver serverReceiver){
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.empty();
    }

    @Override
    public String getHelp() {
        return CommandsEnum.EXIT.commandName + " : " + StringConstants.Commands.EXIT_HELP;
    }
}
