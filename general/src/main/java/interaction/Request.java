package interaction;

import baseclasses.Movie;

import java.io.Serializable;
import java.net.SocketAddress;

public class Request implements Serializable {

    private static final long serialVersionUID = 2837967881502539406L;

    private final String commandName;
    private final String argument;
    private final Movie movie;
    private SocketAddress clientAddres = null;
    private String login;
    private String password;

    public Request(String commandName, String argument, Movie movie) {
        this.commandName = commandName;
        this.argument = argument;
        this.movie = movie;
    }

    public Request(String commandName, Movie movie) {
        this(commandName, null, movie);
    }

    public Request(String commandName, String argument) {
        this(commandName, argument, null);
    }

    public Request(String commandName) {
        this(commandName, null, null);
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgument() {
        return argument;
    }

    public Movie getMovie() {
        return movie;
    }

    public SocketAddress getClientAddres() {
        return clientAddres;
    }

    public void setClientAddres(SocketAddress clientAddres) {
        this.clientAddres = clientAddres;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
