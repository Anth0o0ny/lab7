package commands;

import interaction.Request;
import interaction.Response;
import server.ServerReceiver;
import sub.CommandsEnum;

import java.util.Optional;

public class Authorization extends ServerCommand{
    public Authorization(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.authorization(arg.getLogin(), arg.getPassword()));
    }

    @Override
    public String getHelp() {
        return CommandsEnum.AUTHORIZATION.commandName + " : авторизоваться в системе.";
    }
}
