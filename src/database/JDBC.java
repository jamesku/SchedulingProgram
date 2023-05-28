package database;

import java.sql.DriverManager;
import java.sql.Connection;

/**The main class to connect to the database. Contains connection properties.*/
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone=SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";
    /**Connection instance*/
    public static Connection connection;

    /**This opens the connection to the database.*/
    public static void openConnection(){
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("connection");
        } catch (Exception e){
            System.out.println("failed db connection");
        }
    }

    /**This closes the connection to the database.*/
    public static void closeConnection(){
        try{
            connection.close();
        } catch (Exception e){
            System.out.println("failed to close connection");
        }
    }
}
