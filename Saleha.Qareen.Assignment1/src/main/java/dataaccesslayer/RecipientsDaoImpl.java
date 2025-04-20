/**
 * File: RecipientsDaoImpl.java
 * Author: Saleha Qareen
 * Date: 2025-02-11
 * Description: Implementation of the RecipientsDao interface for database operations.
 */
package dataaccesslayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import transferobjects.RecipientDTO;

/**
 * Implementation of RecipientsDao interface providing required operations for the Recipients table.
 */
public class RecipientsDaoImpl implements RecipientsDao {

    /**
     * Retrieves all recipients from the database.
     * @return List of RecipientDTO objects
     */
    @Override
    public List<RecipientDTO> getAllRecipients() {
        List<RecipientDTO> recipients = new ArrayList<>();
        String query = "SELECT * FROM Recipients ORDER BY AwardID";

        try (Connection con = DataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                RecipientDTO recipient = new RecipientDTO();
                recipient.setAwardID(rs.getInt("AwardID"));
                recipient.setName(rs.getString("Name"));
                recipient.setYear(rs.getInt("Year"));
                recipient.setCity(rs.getString("City"));
                recipient.setCategory(rs.getString("Category"));
                recipients.add(recipient);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching recipients: " + e.getMessage());
        }
        return recipients;
    }

    /**
     * Adds a new recipient to the database.
     * @param recipient the recipient to add
     */
    @Override
    public void addRecipient(RecipientDTO recipient) {
        String query = "INSERT INTO Recipients (Name, Year, City, Category) VALUES (?, ?, ?, ?)";

        try (Connection con = DataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, recipient.getName());
            pstmt.setInt(2, recipient.getYear());
            pstmt.setString(3, recipient.getCity());
            pstmt.setString(4, recipient.getCategory());
            pstmt.executeUpdate();

            // Retrieve and set generated AwardID
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    recipient.setAwardID(generatedKeys.getInt(1));
                }
            }

            System.out.println("\nNew recipient added successfully.");
//            printTableContents();  // Print updated table

        } catch (SQLException e) {
            System.err.println("Error adding recipient: " + e.getMessage());
        }
    }

    /**
     * Deletes a recipient from the database using Award ID.
     * @param awardID the ID of the recipient to delete
     */
    @Override
    public void deleteRecipient(Integer awardID) {
        String query = "DELETE FROM Recipients WHERE AwardID = ?";

        try (Connection con = DataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, awardID);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("\nRecipient deleted successfully.");
//                printTableContents();  // Print updated table
            } else {
                System.out.println("\nRecipient with AwardID " + awardID + " not found.");
            }

        } catch (SQLException e) {
            System.err.println("Error deleting recipient: " + e.getMessage());
        }
    }

    /**
     * Retrieves metadata for the Recipients table, including column names,
     * types, and class mappings.
     * @return A list of string arrays where each array contains column name, SQL type, and Java class name.
     */
    @Override
    public List<String[]> getTableMetadata() {
        List<String[]> metadataList = new ArrayList<>();
        String query = "SELECT * FROM Recipients LIMIT 1";

        try (Connection con = DataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                metadataList.add(new String[]{
                    metaData.getColumnName(i),
                    metaData.getColumnTypeName(i),
                    metaData.getColumnClassName(i)
                });
            }
        } catch (SQLException e) {
            System.err.println("Error fetching metadata: " + e.getMessage());
        }
        return metadataList;
    }

    /**
     * Prints the metadata and contents of the Recipients table in a formatted way.
     */
     @Override
    public void printTableContents() {
        
        // Retrieve and print table data
        List<RecipientDTO> recipients = getAllRecipients();
        if (recipients.isEmpty()) {
            System.out.println("No recipients found.");
        } else {
            System.out.println("\n=== Recipients Table Data ===");
            System.out.printf("%-10s %-25s %-6s %-15s %-20s%n", "AwardID", "Name", "Year", "City", "Category");
            System.out.println("--------------------------------------------------------------------------------");
            for (RecipientDTO r : recipients) {
                System.out.printf("%-10d %-25s %-6d %-15s %-20s%n",
                        r.getAwardID(), r.getName(), r.getYear(), r.getCity(), r.getCategory());
            }
        }
    }
}
