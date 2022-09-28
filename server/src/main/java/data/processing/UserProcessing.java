package data.processing;

import data.dao.UserDAO;
import database.Database;

import java.sql.*;


public class UserProcessing extends Database implements UserDAO {

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    public UserProcessing() {
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQLUser.INIT.QUERY);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Ошибка при обращении к базе данных при создании таблицы users.");;
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }


    @Override
    public boolean create(String login, String password) {
        boolean result = false;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQLUser.INSERT.QUERY);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            result =preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            System.out.println("Ошибка при обращении к базе данных при добавлении пользователя.");;
        } finally {
          closeStatement(preparedStatement);
          closeConnection(connection);
        }
        return result;
    }

    @Override
    public boolean checkExists(String login, String password) {
        boolean result = false;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQLUser.CHECK_EXISTS.QUERY);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            result = preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            System.out.println("Ошибка при обращении к базе данных при проверке пользователя.");;
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public boolean checkImpostor(String login, String password) {
        boolean result = true;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQLUser.CHECK_IMPOSTOR.QUERY);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String dbPassword = resultSet.getString("password");
                result = !dbPassword.equals(password);
            } else {
                return false;
            }
        } catch (SQLException throwables) {
            System.out.println("Ошибка при обращении к базе данных при проверке пользователя.");;
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public boolean remove(String login, String password) {
        boolean result = false;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQLUser.REMOVE.QUERY);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            result = preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            System.out.println("Ошибка при обращении к базе данных при удалении пользователя.");;
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return result;
    }

    public enum SQLUser{
        INSERT("insert into users (login, password) values (?, ?) returning login;"),
        CHECK_EXISTS("select * from users where login = ? and password = ?;"),
        CHECK_IMPOSTOR("select * from users where login = ?;"),
        REMOVE("delete from users where login = ? and password = ? returning login;"),
        INIT("create table if not exists users(login varchar(20) not null primary key, password varchar(150));");

        String QUERY;

        SQLUser(String QUERY) {
            this.QUERY = QUERY;
        }
    }

}
