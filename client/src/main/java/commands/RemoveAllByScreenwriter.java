package commands;

import client.ClientReceiver;
import interaction.Request;
import sub.StringConstants;

import java.util.Optional;

public class RemoveAllByScreenwriter extends ClientCommand{

    public RemoveAllByScreenwriter(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg == null) {
            System.out.println(StringConstants.Commands.CMD_WITH_ARG);
            return Optional.empty();
        }
        return clientReceiver.removeAllByScreenwriter(arg);
    }
}
