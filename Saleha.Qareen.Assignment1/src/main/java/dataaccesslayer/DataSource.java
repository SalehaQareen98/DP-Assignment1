/**
 * File: DataSource.java
 * Author: Saleha Qareen
 * Date: 2025-02-11
 * Description: Manages a single database connection using the Singleton pattern.
 */
package dataaccesslayer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DataSource class provides a single shared database connection.
 */
public class DataSource {

    private static DataSource instance; // Singleton instance
    private static Connection connection; // Shared connection instance

    /**
     * Private constructor to prevent direct instantiation.
     */
    private DataSource() {
        initializeConnection();
    }

    /**
     * Gets the single instance of DataSource.
     * @return DataSource instance
     */
    public static synchronized DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    /**
     * Gets the shared database connection.
     * @return Connection object
     */
    public static synchronized Connection getConnection() {
        try {
            // If connection is closed, reinitialize it
            if (connection == null || connection.isClosed()) {
                initializeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Initializes the database connection using credentials from database.properties.
     */
    private static void initializeConnection() {
        String[] connectionInfo = openPropsFile();
        try {
            connection = DriverManager.getConnection(connectionInfo[0], connectionInfo[1], connectionInfo[2]);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Closes the database connection.
     */
    public static synchronized void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads database connection properties from the properties file.
     * @return String array containing database URL, username, and password
     */
    private static String[] openPropsFile() {
        Properties props = new Properties();

        try (InputStream in = Files.newInputStream(Paths.get("src/main/java/database.properties"))) {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String[]{
            props.getProperty("jdbc.url"),
            props.getProperty("jdbc.username"),
            props.getProperty("jdbc.password")
        };
    }
}
