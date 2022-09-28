package client;

import interaction.Request;
import interaction.Response;
import sub.StringConstants;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.Scanner;

public class Client {

//    private final int PORT = 8013;
    private final int PORT = Integer.parseInt(System.getenv("PORT"));
    private final int BUFFER_SIZE = 1048576;
    private Socket socket;
    private InetAddress address;
    private InputStream inputStream;
    private OutputStream outputStream;
    private static Client client;

    public Client() {

        this.socket = new Socket();
    }

    public void setClient(final Client client) {
        Client.client = client;
    }

    public static Client getClient() {
        return Client.client;
    }

    public boolean connect() {
        try {
            address = InetAddress.getLoopbackAddress();
            socket = new Socket(address, PORT);
            inputStream = socket.getInputStream();
            this.outputStream = socket.getOutputStream();

            System.out.println(StringConstants.Client.CONNECT_SUCCESS);
            return true;
        } catch (IOException e) {
            System.out.println(StringConstants.Client.CONNECT_FAILED);
            return false;
        }

    }

    public boolean reconnect(){
        int number = 1;

        while (!connect()) {
            System.out.println(StringConstants.Client.RECONNECT);
            System.out.println(StringConstants.Client.RECONNECT_TRYNUMBER + number);
            if (number % 3 == 0) {
                System.out.println(StringConstants.Client.RECONNECT_AGAIN);
                Scanner input = new Scanner(System.in);
                String choose = input.nextLine();
                if (choose.equals("No")) {
                    return false;
                }
            }
            number++;
        }

        return true;
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public void sendRequest(Request request){
        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(request);

            byte[] sendArray = byteArrayOutputStream.toByteArray();
            socket.getOutputStream().write(sendArray);
        } catch (SocketException e){
            System.out.println(StringConstants.Client.SEND_REQUEST_CANT_CONNECT);
            reconnect();
        } catch (IOException e) {
            System.out.println(StringConstants.Client.SEND_REQUEST_FAILED);;
        }
    }

    public Optional<Response> getResponse() {

        final ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        try {
            inputStream.read(buffer.array());
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
            ObjectInputStream input = new ObjectInputStream(byteArrayInputStream);

            return Optional.of((Response)input.readObject());
        }
        catch (IOException e) {
            System.out.println(StringConstants.Client.GET_RESPONSE_FAILED);
            return Optional.empty();
        } catch (ClassNotFoundException e) {
            System.out.println(StringConstants.Client.GET_RESPONSE_WRONG_INFO);
            return Optional.empty();
        } catch (NullPointerException e){
             return Optional.empty();
        }
    }
}
