package server;

import baseclasses.Movie;
import baseclasses.MoviesCollection;
import commands.ServerCommand;
import data.processing.MovieProcessing;
import data.processing.UserProcessing;
import interaction.Response;

import sub.StringConstants;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ServerReceiver {

    private Stack<Movie> collection;
    private final Date creationDate;
    private final MoviesCollection mc;
    private final MovieProcessing movieProcessing = new MovieProcessing();
    private final UserProcessing userProcessing = new UserProcessing();

    public ServerReceiver() {

        mc = new MoviesCollection();
        collection = mc.getCollection();
        creationDate = new Date();


    }

    public Response authorization(String login, String password) {
        if (login.isEmpty()) {
            return new Response("Имя пользователя не может быть пустой строкой.");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            password = hashtext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
//            System.out.println("что-то с хэшированием");
        }

        if (userProcessing.checkExists(login, password)) {
            return new Response("");
        } else if (userProcessing.checkImpostor(login, password)) {
            return new Response(login + " : введен неверный пароль для логина");
        } else {
            userProcessing.create(login, password);
            return new Response("");
        }
    }

    public Response info() {
        String[] information = new String[3];
        information[0] = StringConstants.PatternCommands.RECEIVER_INFO_TYPE_COLLECTION + collection.getClass();
        information[1] = StringConstants.PatternCommands.RECEIVER_INFO_AMOUNT + collection.size();
        information[2] = StringConstants.PatternCommands.RECEIVER_INFO_INITIALIZATION_DATE + creationDate;
        return new Response(information);
    }

    public Response help(Map<String, ServerCommand> commandMap) {
        return new Response(commandMap.values().stream().map(ServerCommand::getHelp).toArray(String[]::new));
    }

    public Response show() {
        return new Response(collection.stream().map(Movie::toString).toArray(String[]::new));
    }

    public Response clear(String login) {
        if (movieProcessing.clear(login)) {
            collection.removeIf(movie -> movie.getLogin().equals(login));
            return new Response(StringConstants.PatternCommands.RECEIVER_CLEAR_RESULT);
        } else {
            return new Response("Нет прав");
        }
    }

    public Response shuffle() {
        if (collection.isEmpty()) {
            return new Response(StringConstants.PatternCommands.RECEIVER_EMPTY_COLLECTION_RESULT);
        } else {
            Collections.shuffle(collection);
            StringBuilder stringBuilder = new StringBuilder();
            for (Movie movie : collection) {
                stringBuilder.append(movie).append("; ");
            }
            return new Response(stringBuilder.toString());
        }
    }

    public Response printDescending() {
        if (collection.isEmpty()) {
            return new Response(StringConstants.PatternCommands.RECEIVER_EMPTY_COLLECTION_RESULT);
        } else {
            Stack<Movie> cl = new Stack<>();
            cl.addAll(collection);
            Collections.reverse(cl);
            StringBuilder stringBuilder = new StringBuilder();
            for (Movie movie : cl) {
                stringBuilder.append(movie).append("; ");
            }
            return new Response(stringBuilder.toString());
        }
    }

    public Response groupCountingByTagline() {
        if (collection.isEmpty()) {
            return new Response(StringConstants.PatternCommands.RECEIVER_EMPTY_COLLECTION_RESULT);
        } else {
            ArrayList<String> list = new ArrayList<>();
            for (Movie movie : collection) {
                list.add(movie.getTagline());
            }
            StringBuilder stringBuilder = new StringBuilder();
            Set<String> st = new HashSet<>(list);
            for (String s : st)
                stringBuilder.append("\"").append(s).append("\": ").append(Collections.frequency(list, s)).append("\n");

            return new Response(stringBuilder.toString());
        }
    }

    public Response removeById(String argument, String login) {
        long id;
        try {
            id = Long.parseLong(argument);
        } catch (NumberFormatException e) {
            return new Response("Клиент передал невалидный id.");
        }
        if (movieProcessing.removeById(id, login)) {
            collection.removeIf(movie -> movie.getId().equals(id));
            return new Response(id + ": фильм с данным id удален.");
        } else {
            return new Response("Ошибка удаления по id");
        }
    }

    public Response removeAllByScreenwriter(String arg, String login) {
        if (movieProcessing.removeAllByScreenwriter(arg, login)) {
            collection.removeIf(movie -> movie.getScreenwriter().getName().equals(arg) &&
                    movie.getLogin().equals(login));
            return new Response(StringConstants.PatternCommands.RECEIVER_REMOVE_ALL_BY_SCREENWRITER_RESULT + arg);
        } else {
            return new Response(StringConstants.PatternCommands.RECEIVER_REMOVE_ALL_BY_SCREENWRITER_WROMG_RESULT + arg);
        }
    }

    //
    public Response add(Movie movie, String login) {
        long id = movieProcessing.create(movie, login);
        if (id > 0) {
            movie.setId(id);
            movie.setLogin(login);
            collection.push(movie);
            return new Response("фильм добавлен. id = " + id);
        } else {
            return new Response("фильм с таким же именем уже есть.");
        }
    }

    public Response addIfMin(Movie movie, String login) {
        if (movie.compareTo(Collections.min(collection)) < 0) {
            long id = movieProcessing.create(movie, login);
            movie.setId(id);
            movie.setLogin(login);
            collection.push(movie);
            return new Response(StringConstants.MovieMaking.ADD_SUCCESS);
        } else {
            return new Response(StringConstants.MovieMaking.ADD_FAIL);
        }
    }

    public Response update(String arg, Movie movie, String login) {
        long id;
        try {
            id = Long.parseLong(arg);
        } catch (NumberFormatException e) {
            return new Response("Клиент передал невалидный id.");
        }
        if (movieProcessing.update(id, movie, login)){
            collection.removeIf(movieColl -> movieColl.getId().equals(id));
            movie.setId(id);
            movie.setLogin(login);
            collection.add(movie);
            return new Response(StringConstants.PatternCommands.RECEIVER_UPDATE_RESULT + id);
        } else {
            return new Response(StringConstants.PatternCommands.RECEIVER_UPDATE_WRONG_RESULT);
        }
    }

    public Response insertAt(String argument, Movie movie, String login){

        int index;
        try {
            index = Integer.parseInt(argument) - 1;
        } catch (NumberFormatException e){
            return new Response("Клиент передал невалидный индекс.");
        }
        if (index < 0 && (collection.size() - index > 0)){
            return new Response(StringConstants.PatternCommands.RECEIVER_INSERT_AT_WRONG_RESULT);
        }else {
            long id = movieProcessing.create(movie, login);
            if (id > 0) {
                movie.setId(id);
                movie.setLogin(login);
                collection.insertElementAt(movie, index);
                return new Response("фильм с id = " + id + " добавлен в коллекцию на позицию = " + (index + 1)+
                        "\n" + "База данных не поддерживает вставку в конкретную позицию, поэтому в базу данных " +
                        "элемент добавлен последним.");

            } else {
                return new Response("фильм с таким же именем уже есть.");
            }
        }
    }

    void initCollection(){
        collection = movieProcessing.readAll();
    }
}





