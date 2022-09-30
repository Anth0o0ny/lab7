package database;

import org.apache.commons.dbutils.DbUtils;
import sub.StringConstants;

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
            System.out.println(StringConstants.Server.CANT_CONNECT_TO_BASE);
        }
        return connection;
    }

    protected static void closeConnection(Connection connection){
        try {
            DbUtils.close(connection);
        } catch (SQLException e) {
            System.out.println(StringConstants.Server.CANT_CLOSE_CONNECTION);
        }
    }

    protected static void closeStatement(PreparedStatement preparedStatement){
        try{
            DbUtils.close(preparedStatement);
        } catch (SQLException throwables) {
            System.out.println(StringConstants.Server.CANT_CLOSE_STATEMENT);
        }
    }
}
