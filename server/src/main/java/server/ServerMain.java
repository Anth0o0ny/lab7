package server;

import interaction.Request;
import interaction.Response;
import sub.StringConstants;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Iterator;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class ServerMain {

    private static final ServerReceiver serverReceiver = new ServerReceiver();

    private static final ServerInvoker serverInvoker = new ServerInvoker(serverReceiver);

    public static void main(String[] args) throws IOException, JAXBException {


        Server server = new Server();

        while (true) {

            if (System.in.available() > 0) {
                String servcomment;
                try {
                    servcomment = (new Scanner(System.in)).nextLine();
                } catch (NullPointerException e) {
                    return;
                }
                if (servcomment.equals("save")) {
                    System.out.println(serverReceiver.save());
                }
                else if (servcomment.equals("exit")) {
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

                if (parseComment() == 0){
                    return;
                }else{

                }


                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    server.register();
                } else if (key.isReadable()) {
                    Request request = server.readRequest(key);
                    if (request != null) {
                        Optional<Response> optionalResponse = serverInvoker.execute(request);

                        if (optionalResponse.isPresent()) {
                            Response response = optionalResponse.get();
                            server.sendResponse(response, key);

                        }
                    }
                }
            }
        }
    }
    private static int parseComment() {
        try {
            String comment = "";
            if (System.in.available() > 0) {
                comment = (new Scanner(System.in)).nextLine();
            }
            return comment.compareTo("exit");
        } catch (IOException | NullPointerException e) {
            return 0;
        }
    }
}
