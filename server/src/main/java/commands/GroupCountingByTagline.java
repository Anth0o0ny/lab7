package commands;

import interaction.Request;
import interaction.Response;
import server.ServerReceiver;
import sub.CommandsEnum;
import sub.StringConstants;

import java.util.Optional;

public class GroupCountingByTagline extends ServerCommand{
    public GroupCountingByTagline(ServerReceiver serverReceiver){
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.groupCountingByTagline());
    }

    @Override
    public String getHelp() {
        return CommandsEnum.GROUP_COUNTING_BY_TAGLINE.commandName + " : " + StringConstants.Commands.GROUP_COUNTING_BY_TAGLINE_HELP;
    }
}
