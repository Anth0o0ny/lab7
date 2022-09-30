package client;

import commands.ExecuteScript;
import interaction.Request;
import interaction.Response;
import sub.StringConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;


public class Terminal {

    Scanner scanner;
    private final ClientInvoker clientInvoker;
    private final Client client;
    private String login = "";
    private String password = "";

    public Terminal(ClientInvoker clientInvoker, Client client) {
        this.clientInvoker = clientInvoker;
        this.client = client;
    }

    public void startFile(String filename){
        setScanner(filename);
        if (scanner == null) {
            System.out.println(StringConstants.Commands.EXECUTE_FILE_NOT_EXISTS);
            return;
        }
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            Optional<Request> optRequest = lineParseToCommand(line);
            if (!optRequest.isPresent()) {
                System.out.println(StringConstants.Commands.EXECUTE_RECURSION);
                return;
            } else {
                Request request = optRequest.get();
                if (request.getCommandName().equals("execute_script")) {
                    startFile(request.getArgument());
                    continue;
                }
                request.setLogin(login);
                request.setPassword(password);
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

    public void inputKeyboard() throws NoSuchElementException {
        this.scanner = new Scanner(System.in);


       helloUser();

        while (true) {

            System.out.println(StringConstants.StartTreatment.ENTER_COMMAND);
            System.out.print(">");
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

                    request.setLogin(login);
                    request.setPassword(password);
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

            }
        }
    }

    protected Optional<Request> lineParseToCommand(String line){

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

    private void authorization(){
        System.out.println(StringConstants.StartTreatment.AUTH_ENTER_LOGIN);
        System.out.print(">");
        login = scanner.nextLine();
        System.out.println(StringConstants.StartTreatment.AUTH_ENTER_PASS);
        System.out.print(">");
        password = scanner.nextLine();
        Request authorizateRequest = new Request("authorization");
        authorizateRequest.setLogin(login);
        authorizateRequest.setPassword(password);
        client.sendRequest(authorizateRequest);
        Optional<Response> optionalResponse = client.getResponse();
        if (optionalResponse.isPresent()){
            Response response = optionalResponse.get();
            if (!response.getMessage().isEmpty()){
                System.out.println(response.getMessage());
                authorization();
            }
        }
    }

    private void helloUser(){
        System.out.println(StringConstants.StartTreatment.ASK_TO_AUTH + StringConstants.StartTreatment.Y_OR_N);
        while (true) {
            System.out.print(">");
            String answer = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
            if (answer.equals("y")){
                authorization();
                System.out.println(StringConstants.StartTreatment.ENTER_IN_SYSTEM_BY_NAME + login);
                return;
            }else if (answer.equals("n")){
                System.out.println(StringConstants.StartTreatment.ENTER_IN_SYSTEM_BY_GUEST);
                return;
            }
            System.out.println(StringConstants.StartTreatment.Y_OR_N);
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
