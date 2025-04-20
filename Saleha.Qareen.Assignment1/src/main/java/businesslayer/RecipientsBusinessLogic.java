/**
 * File: RecipientsBusinessLogic.java
 * Author: Saleha Qareen
 * Date: 2025-02-11
 * Description: Handles business logic for managing recipients.
 */
package businesslayer;

import java.util.List;
import dataaccesslayer.RecipientsDao;
import dataaccesslayer.RecipientsDaoImpl;
import transferobjects.RecipientDTO;

/**
 * RecipientsBusinessLogic class contains methods for recipient management.
 */
public class RecipientsBusinessLogic {

    private final RecipientsDao recipientsDao;

    /**
     * Constructor initializes DAO component.
     */
    public RecipientsBusinessLogic() {
        this.recipientsDao = new RecipientsDaoImpl();
    }

    /**
     * Retrieves metadata for the Recipients table.
     *
     * @return A list of string arrays, each containing column name, type, and class name.
     */
    public List<String[]> getTableMetadata() {
        return recipientsDao.getTableMetadata();
    }
    
    /**
     * Retrieves all recipients from the database.
     * @return List of RecipientDTO objects
     */
    public List<RecipientDTO> getAllRecipients() {
        return recipientsDao.getAllRecipients();
    }

    /**
     * Adds a new recipient directly to the database and prints the updated table.
     * @param recipient the recipient to add
     */
    public void addRecipient(RecipientDTO recipient) {
        recipientsDao.addRecipient(recipient);
    }

    /**
     * Deletes a recipient from the database and prints the updated table.
     * @param awardID the ID of the recipient to delete
     */
    public void deleteRecipient(Integer awardID) {
        recipientsDao.deleteRecipient(awardID);
    }
    
    public void printTable(){
        recipientsDao.printTableContents();
    }

}
