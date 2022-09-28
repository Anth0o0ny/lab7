package commands;

import client.ClientReceiver;
import commands.ClientCommand;
import interaction.Request;
import sub.StringConstants;
import client.ClientReceiver;
import java.util.Optional;

public class RemoveById extends ClientCommand {

    public RemoveById(ClientReceiver clientReceiver){
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg == null) {
            System.out.println(StringConstants.Commands.CMD_WITH_ARG);
            return Optional.empty();
        }
        return clientReceiver.removeById(arg);
    }
}
