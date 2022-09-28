package database;

import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/lab7";
    private static final String DB_USER = "Anthony";
    private static final String DB_PASSWORD = "1327";

    protected static Connection getConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException throwables) {
            System.out.println("Не удалось установить соединение с базой данных.");;
        }
        return connection;
    }

    protected static void closeConnection(Connection connection){
        try {
            DbUtils.close(connection);
        } catch (SQLException e) {
            System.out.println("Не удалось закрыть подключение.");
        }
    }

    protected static void closeStatement(PreparedStatement preparedStatement){
        try{
            DbUtils.close(preparedStatement);
        } catch (SQLException throwables) {
            System.out.println("Не удалось закрыть состояние statement.");
        }
    }
}
