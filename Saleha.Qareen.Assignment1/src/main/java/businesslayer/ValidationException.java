/**
 * File: ValidationException.java
 * Author: Saleha Qareen
 * Date: 2025-02-11
 * Description: Custom exception for validation errors.
 */
package businesslayer;

/**
 * ValidationException is thrown when validation rules are violated.
 */
public class ValidationException extends Exception {

    /**
     * Default constructor with a generic error message.
     */
    public ValidationException() {
        super("Data not in valid format");
    }

    /**
     * Constructor with a specific error message.
     * @param message the error message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Constructor with a specific error message and a cause.
     * @param message the error message
     * @param throwable the cause of the exception
     */
    public ValidationException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructor with a cause.
     * @param throwable the cause of the exception
     */
    public ValidationException(Throwable throwable) {
        super(throwable);
    }
}
