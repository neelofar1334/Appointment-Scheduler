package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles database connection
 */
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String DB_userName = "sqlUser"; // Username
    private static String DB_password = "Passw0rd!"; // Password

    /**
     * Opens mysql database connection
     */
    public static Connection openConnection() throws ClassNotFoundException, SQLException {
        Connection connection = null; //local connection object
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, DB_userName, DB_password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Error:" + e.getMessage());
        }
        return connection;
    }

    /**
     * closes mysql database connection
     */
    public static void closeConnection(Connection conn) {
        try {
            conn.close();
            System.out.println("Connection closed!");
        }
        catch(SQLException e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
