package client;

import commands.ExecuteScript;
import interaction.Request;
import interaction.Response;
import sub.StringConstants;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;


public class Terminal {

    Scanner scanner;
    private final ClientInvoker clientInvoker;
    private final Client client;


    public Terminal(ClientInvoker clientInvoker, Client client) {
        this.clientInvoker = clientInvoker;
        this.client = client;
    }

    public void startFile(String filename) throws JAXBException {
        setScanner(filename);
        if (scanner == null) {
            System.out.println(StringConstants.Commands.EXECUTE_FILE_NOT_EXISTS);
            return;
        }
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            Optional<Request> optRequest = lineParseToCommand(line);
            if (!optRequest.isPresent()) {
                System.out.println();
            } else {
                Request request = optRequest.get();
                if (request.getCommandName().equals("execute_script")) {
                    startFile(request.getArgument());
                    continue;
                }
                client.sendRequest(request);
                Optional<Response> optionalResponse = client.getResponse();
                if (!optionalResponse.isPresent()) {
                    System.out.println(StringConstants.StartTreatment.EXECUTE_FAILED);
                } else{
                    Response response = optionalResponse.get();
                    responseProcessing(response);
                }
            }
        }
    }

    public void inputKeyboard() throws JAXBException, NoSuchElementException {
        this.scanner = new Scanner(System.in);

        System.out.println(StringConstants.StartTreatment.START_HELPER);

        while (true) {
            System.out.println(StringConstants.StartTreatment.ENTER_COMMAND);
            String commandLine = scanner.nextLine();
            if (client.isConnected()) {
                Optional<Request> optionalRequest = lineParseToCommand(commandLine);

                if (optionalRequest.isPresent()) {

                    Request request = optionalRequest.get();

                    if (request.getCommandName().equals("execute_script")) {
                        startFile(request.getArgument());
                        ExecuteScript.clearPaths();
                        scanner = new Scanner(System.in);
                        continue;
                    }
                    client.sendRequest(request);

                    Optional<Response> optionalResponse = client.getResponse();
                    if (optionalResponse.isPresent()) {
                        Response response = optionalResponse.get();
                        responseProcessing(response);
                    }
                } else {
                    System.out.println(StringConstants.StartTreatment.COMMAND_NOT_EXISTS);
                }
            } else {
                client.reconnect();
                inputKeyboard();
            }
        }
    }

    protected Optional<Request> lineParseToCommand(String line) throws JAXBException {

        String[] cmdline = line.trim().split(" ");
        String command = cmdline[0].trim();
        if (cmdline.length == 1) {
            return clientInvoker.check(command, null);
        } else if (cmdline.length == 2) {
            return clientInvoker.check(command, cmdline[1]);
        } else {
            return Optional.empty();
        }
    }

    private void setScanner(String filename) {
        File file = new File(filename).getAbsoluteFile();
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException ignored) {
            scanner = null;
        }
    }

    private void responseProcessing(Response response) {
        if (response.getAnswer() == null) {
            System.out.println(response.getMessage());
        } else {
            for (String ans : response.getAnswer()) {
                System.out.println(ans);
            }
        }
    }
}
