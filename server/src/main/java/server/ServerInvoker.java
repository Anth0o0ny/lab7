package server;

import commands.*;
import interaction.Request;
import interaction.Response;
import sub.CommandsEnum;

import javax.xml.bind.JAXBException;
import java.util.HashMap;
import java.util.Optional;

import static sub.CommandsEnum.values;

public class ServerInvoker {
    private final HashMap<String, ServerCommand> commandsMap = new HashMap<>();

    public void registration(String commandName, ServerCommand command) {
        commandsMap.put(commandName, command);
    }

    public ServerInvoker(ServerReceiver serverReceiver){
        for (CommandsEnum commandsEnum : values()){
            Optional<ServerCommand> optional = create(serverReceiver, commandsEnum);
            optional.ifPresent(command -> registration(commandsEnum.commandName, command));
        }
    }

    public Optional<Response> execute(Request request) throws JAXBException {
        String commandName = request.getCommandName();
        if (request.getLogin() == null || request.getPassword() == null
                || request.getLogin().equals("") && !commandName.equals("authorization")) {
            return Optional.of(new Response("Выполнение команд не доступно неавторизованным пользователям.\nВведите authorization, чтобы зарегестрироваться в системе"));
        }

        return this.commandsMap.get(commandName).execute(request);
    }

    public HashMap<String, ServerCommand> getCommandMap() {
        return this.commandsMap;
    }



    private Optional<ServerCommand> create(ServerReceiver serverReceiver, CommandsEnum commandsEnum) {
        switch (commandsEnum){
            case HELP:
                return Optional.of(new Help(serverReceiver, getCommandMap()));
            case INFO:
                return Optional.of(new Info(serverReceiver));
            case SHOW:
                return Optional.of(new Show(serverReceiver));
            case ADD:
                return Optional.of(new Add(serverReceiver));
            case UPDATE:
                return Optional.of(new Update(serverReceiver));
            case REMOVE_BY_ID:
                return Optional.of(new RemoveById(serverReceiver));
            case CLEAR:
                return Optional.of(new Clear(serverReceiver));
            case EXECUTE_SCRIPT:
                return Optional.of(new ExecuteScript(serverReceiver));
            case EXIT:
                return Optional.of(new Exit(serverReceiver));
            case INSERT_AT:
                return Optional.of(new InsertAt(serverReceiver));
            case ADD_IF_MIN:
                return Optional.of(new AddIfMin(serverReceiver));
            case SHUFFLE:
                return Optional.of(new Shuffle(serverReceiver));
            case REMOVE_ALL_BY_SCREENWRITER:
                return Optional.of(new RemoveAllByScreenwriter(serverReceiver));
            case GROUP_COUNTING_BY_TAGLINE:
                return Optional.of(new GroupCountingByTagline(serverReceiver));
            case PRINT_DESCENDING:
                return Optional.of(new PrintDescending(serverReceiver));
            case AUTHORIZATION:
                return Optional.of(new Authorization(serverReceiver));
            default:
                Optional.empty();
        }
        return Optional.empty();
    }
}
