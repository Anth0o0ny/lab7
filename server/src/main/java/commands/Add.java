package commands;

import interaction.Request;
import interaction.Response;
import server.ServerReceiver;
import sub.CommandsEnum;
import sub.StringConstants;

import java.util.Optional;

public class Add extends ServerCommand{
    public Add(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        System.out.println("server Add execut done");
        return Optional.of(serverReceiver.add(arg.getMovie()));
    }

    @Override
    public String getHelp() {
        return CommandsEnum.ADD.commandName + " : " + StringConstants.Commands.ADD_HELP;
    }
}
