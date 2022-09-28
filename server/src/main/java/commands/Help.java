package commands;

import interaction.Request;
import interaction.Response;
import server.ServerReceiver;
import sub.CommandsEnum;
import sub.StringConstants;

import java.util.HashMap;
import java.util.Optional;

public class Help extends ServerCommand{
    private final HashMap<String, ServerCommand> commandsMap;

    public Help(ServerReceiver serverReceiver, HashMap<String, ServerCommand> commandsMap){
        super(serverReceiver);
        this.commandsMap = commandsMap;
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.help(commandsMap));
    }

    @Override
    public String getHelp() {
        return  CommandsEnum.HELP.commandName + " : " + StringConstants.Commands.HELP_HELP;
    }
}
