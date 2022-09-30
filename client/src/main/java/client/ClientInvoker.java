package client;

import commands.*;
import interaction.Request;
import sub.CommandsEnum;

import javax.xml.bind.JAXBException;
import java.util.HashMap;
import java.util.Optional;

import static sub.CommandsEnum.values;

public class ClientInvoker {
    private final HashMap<String, ClientCommand> commandsMap = new HashMap<>();

    public void registration(String commandName, ClientCommand command) {
        commandsMap.put(commandName, command);
    }

    public Optional<Request> check(String commandName, String argument){
        if (this.commandsMap.containsKey(commandName))
            return this.commandsMap.get(commandName).execute(argument);

        return Optional.empty();
    }

    public HashMap<String, ClientCommand> getCommandMap() {
        return this.commandsMap;
    }

    public ClientInvoker(ClientReceiver clientReceiver){

        for (CommandsEnum commandsEnum : values()){
            Optional<ClientCommand> optional = create(clientReceiver, commandsEnum);
            optional.ifPresent(command -> registration(commandsEnum.commandName, command));
        }
    }

    private Optional<ClientCommand> create(ClientReceiver clientReceiver, CommandsEnum commandsEnum) {
        switch (commandsEnum){
            case HELP:
                return Optional.of(new Help(clientReceiver));
            case INFO:
                return Optional.of(new Info(clientReceiver));
            case SHOW:
                return Optional.of(new Show(clientReceiver));
            case ADD:
                return Optional.of(new Add(clientReceiver));
            case UPDATE:
                return Optional.of(new Update(clientReceiver));
            case REMOVE_BY_ID:
                return Optional.of(new RemoveById(clientReceiver));
            case CLEAR:
                return Optional.of(new Clear(clientReceiver));

            case EXECUTE_SCRIPT:
                return Optional.of(new ExecuteScript(clientReceiver));
            case EXIT:
                return Optional.of(new Exit(clientReceiver));
            case INSERT_AT:
                return Optional.of(new InsertAt(clientReceiver));
            case ADD_IF_MIN:
                return Optional.of(new AddIfMin(clientReceiver));
            case SHUFFLE:
                return Optional.of(new Shuffle(clientReceiver));
            case REMOVE_ALL_BY_SCREENWRITER:
                return Optional.of(new RemoveAllByScreenwriter(clientReceiver));
            case GROUP_COUNTING_BY_TAGLINE:
                return Optional.of(new GroupCountingByTagline(clientReceiver));
            case PRINT_DESCENDING:
                return Optional.of(new PrintDescending(clientReceiver));
            case AUTHORIZATION:
            default:
                return Optional.empty();
        }
    }
}
