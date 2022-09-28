package commands;

import interaction.Request;
import interaction.Response;
import server.ServerReceiver;
import sub.CommandsEnum;
import sub.StringConstants;

import java.util.Optional;

public class PrintDescending extends ServerCommand{
    public PrintDescending(ServerReceiver serverReceiver){
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.printDescending());
    }

    @Override
    public String getHelp() {
        return CommandsEnum.PRINT_DESCENDING.commandName + " : " + StringConstants.Commands.PRINT_DESCENDING_HELP;
    }
}
