/**
 * File: RecipientsDao.java
 * Author: Saleha Qareen
 * Date: 2025-02-11
 * Description: Interface defining the DAO operations for Recipients.
 */
package dataaccesslayer;

import java.util.List;
import transferobjects.RecipientDTO;

/**
 * DAO interface for Recipients table operations.
 */
public interface RecipientsDao {
    
    /**
     * Retrieves all recipients from the database.
     * @return List of RecipientDTO objects
     */
    List<RecipientDTO> getAllRecipients();

    /**
     * Retrieves metadata for the Recipients table.
     * @return A list of string arrays where each array contains column name,
     * type, and class name.
     */
    List<String[]> getTableMetadata();

    /**
     * Adds a new recipient to the database.
     * @param recipient the recipient to add
     */
    void addRecipient(RecipientDTO recipient);
    
    /**
     * Deletes a recipient from the database using Award ID.
     * @param awardID the ID of the recipient to delete
     */
    void deleteRecipient(Integer awardID);

    void printTableContents();
}
