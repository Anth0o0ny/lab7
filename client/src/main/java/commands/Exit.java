package commands;

import client.ClientReceiver;
import interaction.Request;
import sub.StringConstants;

import java.util.NoSuchElementException;
import java.util.Optional;

public class Exit extends ClientCommand{
    public Exit(ClientReceiver clientReceiver){
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println(StringConstants.Commands.CMD_WITHOUT_ARG);
            return Optional.empty();
        }
        System.out.println("Спасибо за работу");
        throw new NoSuchElementException();
    }
}
