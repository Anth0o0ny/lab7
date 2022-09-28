package commands;

import interaction.Request;
import interaction.Response;
import server.ServerReceiver;
import sub.CommandsEnum;
import sub.StringConstants;

import java.util.Optional;

public class Update extends ServerCommand{
    public Update(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.update(arg.getArgument(), arg.getMovie() ));
    }

    @Override
    public String getHelp() {
        return CommandsEnum.UPDATE.commandName + " id : " + StringConstants.Commands.UPDATE_BY_ID_HELP;
    }
}
