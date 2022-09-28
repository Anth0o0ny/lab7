package moviemaking;

import baseclasses.Coordinates;
import baseclasses.Movie;
import baseclasses.MpaaRating;
import baseclasses.Person;
import sub.StringConstants;
import input.InputArgumentTester;

import java.util.Collections;
import java.util.Date;
import java.util.Stack;

public class AddMovie {

    private static IdGenerator idGenerator;

    public static void setIdGenerator(IdGenerator idGenerator){
        AddMovie.idGenerator = idGenerator;
    }

    public static String addMovie(Stack<Movie> collection) {
        Movie makingMovie = makeMovie();
        collection.push(makingMovie);
        return StringConstants.MovieMaking.ADD_SUCCESS;
    }

    public static String AddMovieIfMin(Stack<Movie> collection) {
        Movie makingMovie = makeMovie();
        if (makingMovie.compareTo(Collections.min(collection)) < 0) {
            collection.push(makingMovie);
            return StringConstants.MovieMaking.ADD_SUCCESS;
        } else {
            return StringConstants.MovieMaking.ADD_FAIL;
        }
    }

    public static Movie makeMovie() {

        InputArgumentTester iat = new InputArgumentTester();

//        long id = idGenerator.generateId();

        String name = iat.assignInputName() ;
        Double x = iat.assignInputX();
        Float y = iat.assignInputY();
        Coordinates coordinates = new Coordinates(x, y);
        Date date = new Date();
        Long oscCount = iat.assignInputOscarCount();
        long budget = iat.assignInputBudget();
        String tagline = iat.assignTagline();
        MpaaRating rate = CreatePerson.chooseRating();
        Person person = CreatePerson.ctreatePerson();

        return new Movie( name, coordinates, date, oscCount, budget, tagline, rate, person);
    }
}
