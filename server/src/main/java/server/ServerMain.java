package server;

import interaction.Request;
import interaction.Response;
import sub.StringConstants;


import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Iterator;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ServerMain {

    private static final ServerReceiver serverReceiver = new ServerReceiver();

    private static final ServerInvoker serverInvoker = new ServerInvoker(serverReceiver);

    public static void main(String[] args) throws IOException {


        Server server = new Server();

        serverReceiver.initCollection();

        while (true) {

            if (System.in.available() > 0) {
                String servcomment;
                try {
                    servcomment = (new Scanner(System.in)).nextLine();
                } catch (NullPointerException e) {
                    return;
                }
                if (servcomment.equals("exit")) {
                    System.out.println(StringConstants.Server.EXIT_RESULT);
                    System.exit(0);
                } else {
                    System.out.println(StringConstants.Server.WRONG_COMMAND);
                }


            }


            server.getSelector().select(3000);
            Set<SelectionKey> keys = server.getSelector().selectedKeys();
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                if (parseComment()) {
                    return;
                } else {
                }
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    server.register();
                } else if (key.isReadable()) {
                    class Action extends RecursiveAction {
                        @Override
                        protected void compute() {
                            Request request = server.readRequest(key);
                            if (request == null) {
                                disconnectClient();
                                return;
                            }
                            class Action2 extends RecursiveAction {
                                @Override
                                protected void compute() {
                                    Optional<Response> optionalResponse = null;

                                        optionalResponse = serverInvoker.execute(request);


                                    if (optionalResponse.isPresent()) {
                                        Response response = optionalResponse.get();
                                        server.sendResponse(response, key);
                                    }
                                }
                            }
                            ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
                            forkJoinPool.invoke(new Action2());
                        }
                    }
                    ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
                    forkJoinPool.invoke(new Action());
                }
            }
        }
    }

    private static void disconnectClient() {
        System.out.println("Клиент отключен.");
    }

    private static boolean parseComment() {
        try {
            String comment = "";
            if (System.in.available() > 0) {
                comment = (new Scanner(System.in)).nextLine();
            }
            return comment.equals("exit");
        } catch (IOException | NullPointerException e) {
            return false;
        }
    }
}
