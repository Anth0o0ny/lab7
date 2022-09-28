package commands;

import client.ClientReceiver;
import interaction.Request;
import sub.CommandsEnum;

import java.util.Optional;

public class Help extends ClientCommand{
    public Help(ClientReceiver clientReceiver){
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null){
            System.out.println(CommandsEnum.HELP.name() + " : rb создать");
            return Optional.empty();
        }
        return Optional.of(new Request("help"));
    }
}
