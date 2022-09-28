package commands;


import interaction.Request;
import client.ClientReceiver;
import sub.StringConstants;
import sub.CommandsEnum;

import javax.xml.bind.JAXBException;
import java.util.Optional;

public class Info extends ClientCommand {


    public Info(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }


    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println(StringConstants.Commands.CMD_WITHOUT_ARG);
            return Optional.empty();
        }
        return Optional.of(new Request("info"));
}}

