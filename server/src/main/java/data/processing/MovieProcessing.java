package data.processing;

import baseclasses.Movie;
import data.dao.MovieDAO;
import database.Database;

import java.sql.*;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

public class MovieProcessing extends Database implements MovieDAO {

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    public MovieProcessing() {
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQLMovie.INIT.QUERY);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Ошибка при обращении к базе данных при создании таблицы movies");
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }

    }


    @Override
    public int create(Movie movie, String login) {
        int result = -1;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQLMovie.INSERT.QUERY);
            preparedStatement.setString(1, movie.getName());
            preparedStatement.setDouble(2, movie.getCoordinates().getX());
            preparedStatement.setFloat(3, movie.getCoordinates().getY());
            preparedStatement.setDate(4, new java.sql.Date(movie.getCreationDate().getTime()));
            preparedStatement.setLong(5, movie.getOscarsCount());
            preparedStatement.setLong(6, movie.getBudget());
            preparedStatement.setString(7, movie.getTagline());
            preparedStatement.setString(8, movie.getMpaaRating().toString());
            preparedStatement.setString(9, movie.getScreenwriter().getName());
            preparedStatement.setFloat(10, movie.getScreenwriter().getHeight());
            preparedStatement.setString(11, movie.getScreenwriter().getHairColor().toString());
            preparedStatement.setString(12, movie.getScreenwriter().getNationality().toString());
            preparedStatement.setString(13, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("id");
                return result;
            } else {
                return result;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
//            System.out.println("Ошибка при обращении к базе данных при добавлении города.");
        } finally {
          closeStatement(preparedStatement);
          closeConnection(connection);
        }
        return result;
    }

    @Override
    public Stack<Movie> readAll() {
        Stack<Movie> result = new Stack<>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQLMovie.READ_ALL.QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                long id = resultSet.getInt(1);
                String name = resultSet.getString("name");
                Double x = resultSet.getDouble("x");
                Float y = resultSet.getFloat("y");
                java.util.Date creationDate = resultSet.getDate("creation_date");
                Long oscarCount = resultSet.getLong("oscar_count");
                long budget = resultSet.getLong("budget");
                String tagline = resultSet.getString("tagline");
                String mpaaRating = resultSet.getString("mpaa_rating");
                String personName = resultSet.getString("person_name");
                float height = resultSet.getFloat("person_height");
                String hairColor = resultSet.getString("hair_color");
                String nationality = resultSet.getString("person_nationality");
                String login = resultSet.getString("login");
                Movie movie = null;
                try{
                    movie = new Movie(id, name, x, y, creationDate, oscarCount, budget, tagline, mpaaRating,
                            personName, height, hairColor, nationality, login);
                } catch (NullPointerException ex) {
                    System.out.println("В базе данных обнаружен невалидный фильм. Он немедленно будет удален.");
//                    deleteInvalidMove(name);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
//            System.out.println("Ошибка при обращении к базе данных при чтении содержимого.");
            return new Stack<>();
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return result;
    }

    private enum SQLMovie {
        INSERT("insert into movies (name, x, y, creation_date, oskar_count, budget, tagline, mpaa_rating, "
                + "person_name, person_height, hair_color, person_nationality, login) "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?,?) returning id;"),
        READ_ALL("select * from movies;"),
        INIT("create table if not exists movies(id serial not null primary key, name varchar(50) not null unique, "
                + "x double precision not null, y double precision not null, creation_date date, oskar_count bigint, budget bigint, "
                + "tagline varchar(50), mpaa_rating varchar(50), person_name varchar(50), person_height float, "
                + "hair_color varchar(50), person_nationality varchar(20), login varchar(50), "
                + "foreign key (login) references users (login));");
        String QUERY;

        SQLMovie(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}