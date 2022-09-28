package commands;

import interaction.Request;
import interaction.Response;
import server.ServerReceiver;
import sub.CommandsEnum;
import sub.StringConstants;

import java.util.Optional;

public class Show extends ServerCommand{
    public Show(ServerReceiver serverReceiver){
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.show());
    }

    @Override
    public String getHelp() {
        return CommandsEnum.SHOW.commandName + " : " + StringConstants.Commands.SHOW_HELP;
    }
}
