package client;

import sub.StringConstants;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

public class ClientMain {
    public static void main(String[] args){

        Client client = new Client();
        ClientReceiver clientReceiver = new ClientReceiver();
        ClientInvoker clientInvoker = new ClientInvoker(clientReceiver);
        client.setClient(client);

        client.connect();


        Terminal terminal = new Terminal(clientInvoker, client);
        try {
            terminal.inputKeyboard();
        }catch (NoSuchElementException ignore){

        } finally {
            System.out.println(StringConstants.Client.CLIENT_END_WORK);
        }


    }
}
