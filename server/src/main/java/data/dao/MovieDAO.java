package data.dao;

import baseclasses.Movie;

import java.util.SortedSet;
import java.util.Stack;

public interface MovieDAO {

    int create(Movie movie, String login);

    Stack<Movie> readAll();

    boolean clear(String login);

    boolean removeById(long id, String login);

    boolean removeAllByScreenwriter(String screenwriter, String login);

    boolean update(long id, Movie movie, String login);
}
