/**
 * File: SalehaQareenAssignment1.java
 * Author: Saleha Qareen
 * Date: 2025-02-11
 * Description: Console-based application that inserts and deletes a recipient while displaying table contents.
 */

import businesslayer.RecipientsBusinessLogic;
import transferobjects.RecipientDTO;

import java.util.List;

/**
 * The SalehaQareenAssignment1 class demonstrates basic CRUD operations
 * on the Recipients table using the Business Logic Layer.
 */
public class SalehaQareenAssignment1 {
    
    /**
     * The main entry point of the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        RecipientsBusinessLogic businessLogic = new RecipientsBusinessLogic();
        
        // Display table before insertion
        System.out.println("\n=== Recipients Table Before Insertion ===");
        businessLogic.printTable();
        
        // Create and insert a new recipient
        RecipientDTO newRecipient = new RecipientDTO();
        newRecipient.setName("TESTADMIN");
        newRecipient.setYear(2025);
        newRecipient.setCity("Ottawa");
        newRecipient.setCategory("Programming");
        businessLogic.addRecipient(newRecipient);
        
        // Display table after insertion
        System.out.println("\n=== Recipients Table After Insertion ===");
        businessLogic.printTable();
        
        // Delete the newly added recipient
        businessLogic.deleteRecipient(newRecipient.getAwardID());
        
        // Display table after deletion
        System.out.println("\n=== Recipients Table After Deletion ===");
        businessLogic.printTable();
        
        // Display table metadata
        System.out.println("\n=== Recipients Table Metadata ===");
        List<String[]> metadata = businessLogic.getTableMetadata();
        System.out.printf("%-20s %-15s %-30s%n", "Column Name", "SQL Type", "Java Class");
        System.out.println("---------------------------------------------------------------------");
        
        for (String[] meta : metadata) {
            System.out.printf("%-20s %-15s %-30s%n", meta[0], meta[1], meta[2]);
        }
    }
}
