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
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32){
                hashtext = "0" + hashtext;
            }
            password = hashtext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
//            System.out.println("что-то с хэшированием");
        }

            if(userProcessing.checkExists(login, password)){
                return new Response("");
            }else if (userProcessing.checkImpostor(login, password)){
               return  new Response(login + " : введен неверный пароль для логина" );
            } else {
                userProcessing.create(login, password);
                return new Response("");
            }
    }

    public Response info(){
        String[] information = new String[3];
        information[0] = StringConstants.PatternCommands.RECEIVER_INFO_TYPE_COLLECTION  + collection.getClass();
        information[1] = StringConstants.PatternCommands.RECEIVER_INFO_AMOUNT + collection.size();
        information[2] = StringConstants.PatternCommands.RECEIVER_INFO_INITIALIZATION_DATE + creationDate;
        return new Response(information);
    }

    public Response help(Map<String, ServerCommand> commandMap) {
        return new Response(commandMap.values().stream().map(ServerCommand::getHelp).toArray(String[]::new));
    }

    public Response show(){
        return new Response(collection.stream().map(Movie::toString).toArray(String[]::new));
    }

    public Response clear(){
        collection.clear();
        return new Response(StringConstants.PatternCommands.RECEIVER_CLEAR_RESULT);
    }

    public Response shuffle(){
        if (collection.isEmpty()) {
            return new Response(StringConstants.PatternCommands.RECEIVER_EMPTY_COLLECTION_RESULT)   ;
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
    //
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

    public Response removeById(String argument) {
        String str = "";

        try {
           long id = Long.parseLong(argument);
        } catch (NumberFormatException e){
            return new Response("Клиент передал невалидный id.");
        }
        if (collection.isEmpty()) {
            return new Response(StringConstants.PatternCommands.RECEIVER_EMPTY_COLLECTION_RESULT);
        } else {
            for (Movie movie : collection) {
                if (String.valueOf(movie.getId()).equals(argument)) {
                    collection.remove(movie);
                    str = StringConstants.PatternCommands.RECEIVER_REMOVE_BY_ID_ACTION + argument + ".";
                    break;

                } else {
                    str = StringConstants.PatternCommands.RECEIVER_REMOVE_BY_ID_WRONG_ACTION;
                }
            }
            return new Response(str);
        }
    }
    //
    public Response removeAllByScreenwriter(String arg) {
        boolean flag = false;
        if (collection.isEmpty()) {
            return new Response(StringConstants.PatternCommands.RECEIVER_EMPTY_COLLECTION_RESULT);
        } else {
            List<Movie> found = new ArrayList<>();
            for (Movie movie : collection) {
                if (String.valueOf(movie.getScreenwriter()).equals(arg)) {
                    System.out.println(movie);
                    found.add(movie);
                    flag = true;
                }
            }
            if (flag) {
                collection.removeAll(found);
                return new Response(StringConstants.PatternCommands.RECEIVER_REMOVE_ALL_BY_SCREENWRITER_RESULT + arg);
            } else {
                return new Response(StringConstants.PatternCommands.RECEIVER_REMOVE_ALL_BY_SCREENWRITER_WROMG_RESULT + arg) ;
            }
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

//        movie.setId();
//        collection.push(movie);
//        return new Response(StringConstants.MovieMaking.ADD_SUCCESS);
    }

    public Response addIfMin(Movie movie){
        if (movie.compareTo(Collections.min(collection)) < 0) {
//            long id = idGenerator.generateId();
//            movie.setId(id);
            collection.push(movie);
            return  new Response(StringConstants.MovieMaking.ADD_SUCCESS);
        } else {
            return new Response(StringConstants.MovieMaking.ADD_FAIL);
        }
    }

    public Response update(String arg, Movie movie){
        String str = "";

        for (Movie movie1 : collection) {

            if (String.valueOf(movie1.getId()).equals(arg)) {

                  long id = movie1.getId();

                  movie.setId(id);
                  collection.setElementAt(movie, (collection.size() - collection.search(movie1)));

                  str = StringConstants.PatternCommands.RECEIVER_UPDATE_RESULT + id;

                break;

            } else {

                str = StringConstants.PatternCommands.RECEIVER_UPDATE_WRONG_RESULT;

            }
        } return new Response(str);
    }

    public Response insertAt(String argument, Movie movie){

        String str;
        int index = Integer.parseInt(argument);
        if (index < 0 ){
            str = StringConstants.PatternCommands.RECEIVER_INSERT_AT_WRONG_RESULT;
        }else{
            if ((collection.size() - index > 0)){
//                long id = idGenerator.generateId();
//                movie.setId(id);
                collection.insertElementAt(movie,index);

                str = StringConstants.PatternCommands.RECEIVER_INSERT_AT_RESULT;
            } else{
                str = StringConstants.PatternCommands.RECEIVER_INSERT_AT_WRONG_RESULT;
            }

        }
        return new Response(str);
    }
    public String save(){
//        Parser.parsingToXml(mc);
        return StringConstants.PatternCommands.RECEIVER_SAVE_RESULT;
    }

    void initCollection(){
        collection = movieProcessing.readAll();
    }
}





