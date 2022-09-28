package commands;

import interaction.Request;
import interaction.Response;
import server.ServerReceiver;
import sub.CommandsEnum;
import sub.StringConstants;

import java.util.Optional;

public class ExecuteScript extends ServerCommand {

    public ExecuteScript(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.empty();
    }

    @Override
    public String getHelp() {
        return CommandsEnum.EXECUTE_SCRIPT.commandName + " file_name : " + StringConstants.Commands.EXECUTE_SCRIPT_HELP;
    }
}
