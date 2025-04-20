/**
 * DataSource.java
 *
 * Assignment 2 – CST8288_020_021  
 * Student Name: Saleha Qareen  
 * Student Number: 041161192  
 * Date: 03/25/2025
 *
 * Description:
 * Singleton class that manages database credentials and provides JDBC connections.
 */

package dataaccesslayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class responsible for providing database connections.
 */
public class DataSource {
    private static volatile DataSource instance;
    private final String url = "jdbc:mysql://localhost:3306/books?useSSL=false&allowPublicKeyRetrieval=true";
    private String user;
    private String password;

    private DataSource() {
        // Explicitly load the MySQL JDBC driver for clarity
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }

    /**
     * Returns the singleton instance of DataSource.
     * 
     * @return the singleton instance
     */
    public static DataSource getInstance() {
        if (instance == null) {
            synchronized (DataSource.class) {
                if (instance == null) instance = new DataSource();
            }
        }
        return instance;
    }

    /**
     * Sets the database credentials.
     * 
     * @param user the database username
     * @param password the database password
     */
    public void setCredentials(String user, String password) {
        this.user = user;
        this.password = password;
    }

    /**
     * Creates a new Connection instance.
     * 
     * @return a new database Connection
     * @throws SQLException if a database access error occurs or credentials are missing
     */
    public Connection createConnection() throws SQLException {
        if (user == null || password == null) {
            throw new SQLException("Database credentials must be set before calling createConnection().");
        }
        return DriverManager.getConnection(url, user, password);
    }
}
