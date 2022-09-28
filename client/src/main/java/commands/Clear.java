package commands;

import client.ClientReceiver;
import interaction.Request;
import sub.StringConstants;

import java.util.Optional;

public class Clear extends ClientCommand{
    public Clear(ClientReceiver clientReceiver){
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println(StringConstants.Commands.CMD_WITHOUT_ARG);
            return Optional.empty();
        }
        return Optional.of(new Request("clear"));
    }
}
