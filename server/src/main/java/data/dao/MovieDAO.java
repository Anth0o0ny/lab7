package data.dao;

import baseclasses.Movie;

import java.util.SortedSet;
import java.util.Stack;

public interface MovieDAO {
    int create(Movie movie, String login);

    Stack<Movie> readAll();

}
