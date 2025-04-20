/**
 * SalehaQareenLab1
 *
 * This program connects to a database, retrieves award recipient details from the `Recipients` table
 * for a randomly chosen year between 1987 and 2020, and displays the results in a structured format.
 * If no results are found for the given year, it informs the user accordingly.
 *
 * @author Saleha Qareen
 * @version 1.0
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Properties;

public class SalehaQareenLab1 {

    /**
     * Main method that executes the database query and displays results.
     *
     * @param args Command line arguments (not used in this program).
     */
    public static void main(String[] args) {
        Properties props = new Properties();

        // Load database connection properties
        try (InputStream in = Files.newInputStream(Paths.get("src/main/java/database.properties"))) {
            props.load(in);
        } catch (IOException e) {
            System.err.println("Error loading database properties: " + e.getMessage());
            return;
        }

        // Retrieve database credentials from properties file
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        // Generate a random year between 1987 and 2020
        SecureRandom random = new SecureRandom();
        int randomYear = 1987 + random.nextInt(2020 - 1987 + 1);

        // SQL query to fetch recipients for a given year
        String sql = "SELECT AwardID, Name, Year, City, Category FROM Recipients WHERE Year = ?";

        // Establish database connection and execute query
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, randomYear); // Set year parameter in query

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                boolean hasResults = resultSet.isBeforeFirst(); // Check if there are results

                if (hasResults) {
                    displayTableHeader(resultSet);
                } else {
                    System.out.println("\nNo results found for year: " + randomYear);
                }

                // Print retrieved records
                while (resultSet.next()) {
                    int awardId = resultSet.getInt("AwardID");
                    String name = resultSet.getString("Name");
                    int year = resultSet.getInt("Year");
                    String city = resultSet.getString("City");
                    String category = resultSet.getString("Category");

                    System.out.printf("%-10s %-25s %-6s %-15s %-20s%n", awardId, name, year, city, category);
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Database error: " + sqlException.getMessage());
        }
    }

    /**
     * Displays table column attributes and headers.
     *
     * @param resultSet The result set containing column metadata.
     * @throws SQLException If there is an error accessing metadata.
     */
    private static void displayTableHeader(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int numberOfColumns = metaData.getColumnCount();

        System.out.println("\nRecipients Table - Column Attributes:");
        for (int i = 1; i <= numberOfColumns; i++) {
            System.out.printf("%-15s %-10s %-20s%n",
                    metaData.getColumnName(i),
                    metaData.getColumnTypeName(i),
                    metaData.getColumnClassName(i));
        }
        System.out.println();

        // Print column headers
        System.out.printf("%-10s %-25s %-6s %-15s %-20s%n", "AwardID", "Name", "Year", "City", "Category");
        System.out.println();
    }
}
