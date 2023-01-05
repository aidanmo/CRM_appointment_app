package Utils;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//ec2-3-88-173-245.compute-1.amazonaws.com/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";

    //private static final String driver = "com.mysql.jdbc.Driver";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "aidan";
    private static final String password = "pasword123";

    public static Connection connection;

    /**
     * Opening connection to SQL database so data can be queried, added, deleted, and updated.
     */
    public static void openConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection Successful!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Close connection once the program has completed and been closed.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection Closed!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }



}
