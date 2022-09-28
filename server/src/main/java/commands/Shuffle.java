package commands;

import interaction.Request;
import interaction.Response;
import server.ServerReceiver;
import sub.CommandsEnum;
import sub.StringConstants;

import java.util.Optional;

public class Shuffle extends ServerCommand{
    public Shuffle(ServerReceiver serverReceiver){
        super(serverReceiver);
    }


    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.shuffle());
    }

    @Override
    public String getHelp() {
        return CommandsEnum.SHUFFLE.commandName + " : " + StringConstants.Commands.SHUFFLE_HELP;
    }
}
