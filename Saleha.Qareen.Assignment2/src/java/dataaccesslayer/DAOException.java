/**
 * DAOException.java
 *
 * Assignment 2 – CST8288_020_021  
 * Student Name: Saleha Qareen  
 * Student Number: 041161192  
 * Date: 03/25/2025
 *
 * Description:
 * Custom exception class for handling DAO-related errors such as database access issues.
 */

package dataaccesslayer;

/**
 * Custom exception class used for handling errors in the Data Access Layer (DAO).
 * This allows the separation of database-specific exceptions from the rest of the application.
 */
public class DAOException extends Exception {

    /**
     * Constructs a DAOException with a descriptive message.
     *
     * @param message the detail message explaining the reason for the exception.
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructs a DAOException with a message and the original cause of the exception.
     *
     * @param message the detail message explaining the error.
     * @param cause   the original throwable cause (such as SQLException).
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
