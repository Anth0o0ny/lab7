package commands;

import interaction.Request;
import interaction.Response;
import server.ServerReceiver;
import sub.CommandsEnum;
import sub.StringConstants;

import java.util.Optional;

public class AddIfMin extends ServerCommand{
    public AddIfMin(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.addIfMin(arg.getMovie()));
    }

    @Override
    public String getHelp() {
        return CommandsEnum.ADD_IF_MIN.commandName + " : " + StringConstants.Commands.ADD_IF_MIN_HELP;
    }
}
