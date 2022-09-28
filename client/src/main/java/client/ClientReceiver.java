package client;

import input.InputArgumentTester;
import interaction.Request;
import moviemaking.AddMovie;

import java.util.Optional;

public class ClientReceiver {


      private final InputArgumentTester inputArgumentTester;
      private final AddMovie addMovie;
    public ClientReceiver() {
        this.addMovie = new AddMovie();
        inputArgumentTester = new InputArgumentTester();

    }

    public Optional<Request> removeById(String arg) {
        Long id = inputArgumentTester.assignInputId(arg);
        if (id == null) {
            return Optional.empty();
        }
        return Optional.of(new Request("remove_by_id", arg));
    }

    public Optional<Request> removeAllByScreenwriter(String arg) {
            return Optional.of(new Request("remove_all_by_screenwriter", arg));
    }

    public Optional<Request> insertAt(String arg){
        Long position = Long.parseLong(arg);
        if (position > 0){
            return Optional.of(new Request("insert_at", arg, AddMovie.makeMovie()));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Request> add() {
        return Optional.of(new Request("add", AddMovie.makeMovie()));

    }

    public Optional<Request> addIfMin() {
        System.out.println("ClientReceiver addIfMin");
        return Optional.of(new Request("add_if_min", AddMovie.makeMovie()));
    }

    public Optional<Request> update(String arg){
        Long id = inputArgumentTester.assignInputId(arg);
        return  Optional.of(new Request("update", arg, AddMovie.makeMovie()));
    }

}
