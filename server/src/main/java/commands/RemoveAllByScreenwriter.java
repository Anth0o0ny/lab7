package commands;

import interaction.Request;
import interaction.Response;
import server.ServerReceiver;
import sub.CommandsEnum;
import sub.StringConstants;

import java.util.Optional;

public class RemoveAllByScreenwriter extends ServerCommand{


    public RemoveAllByScreenwriter(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.removeAllByScreenwriter(arg.getArgument(), arg.getLogin()));
    }

    @Override
    public String getHelp() {
        return CommandsEnum.REMOVE_ALL_BY_SCREENWRITER.commandName + " screenwriter : " + StringConstants.Commands.REMOVE_ALL_BY_SCREENWRITER_HELP;
    }
}
