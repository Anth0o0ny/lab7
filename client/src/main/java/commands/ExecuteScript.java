package commands;

import client.ClientReceiver;
import interaction.Request;
import sub.StringConstants;

import java.io.File;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ExecuteScript extends ClientCommand{

    private static final Set<String> paths = new HashSet<>();

    public ExecuteScript(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg == null) {
            System.out.println(StringConstants.Commands.CMD_WITH_ARG);
            return Optional.empty();
        }
        String path = new File(arg).getAbsolutePath();
        if (paths.add(path)) {
            return Optional.of(new Request("execute_script", arg));
        } else {
            System.out.println("Обаружена рекурсия");
            return Optional.empty();
        }
    }

    public static void clearPaths() {
        paths.clear();
    }
}

