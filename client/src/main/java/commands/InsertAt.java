package commands;

import client.ClientReceiver;
import interaction.Request;
import sub.StringConstants;

import java.util.Optional;

public class InsertAt extends ClientCommand{
    public InsertAt(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg == null) {
            System.out.println(StringConstants.Commands.CMD_WITH_ARG);
            return Optional.empty();
        }
        return clientReceiver.insertAt(arg);
    }
}
