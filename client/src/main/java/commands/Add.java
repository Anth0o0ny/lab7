package commands;

import client.ClientReceiver;
import interaction.Request;
import sub.StringConstants;

import java.util.Optional;

public class Add extends ClientCommand{

    public Add(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println(StringConstants.Commands.CMD_WITHOUT_ARG);
            return Optional.empty();
        }
        return clientReceiver.add();
    }
}
