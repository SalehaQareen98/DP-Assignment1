/**
 * File: RecipientValidation.java
 * Author: Saleha Qareen
 * Date: 2025-02-11
 * Description: Handles validation for recipient data.
 */
package businesslayer;

import transferobjects.RecipientDTO;

/**
 * RecipientValidation class validates recipient data before processing.
 */
public class RecipientValidation {

    private static final int NAME_MAX_LENGTH = 50;
    private static final int CITY_MAX_LENGTH = 30;
    private static final int CATEGORY_MAX_LENGTH = 40;

    /**
     * Cleans white space from recipient fields.
     * @param recipient the recipient to clean
     */
    public static void cleanRecipient(RecipientDTO recipient) {
        if (recipient.getName() != null) {
            recipient.setName(recipient.getName().trim());
        }
        if (recipient.getCity() != null) {
            recipient.setCity(recipient.getCity().trim());
        }
        if (recipient.getCategory() != null) {
            recipient.setCategory(recipient.getCategory().trim());
        }
    }

    /**
     * Validates recipient fields to ensure data integrity.
     * @param recipient the recipient to validate
     * @throws ValidationException if validation fails
     */
    public static void validateRecipient(RecipientDTO recipient) throws ValidationException {
        validateString(recipient.getName(), "Name", NAME_MAX_LENGTH, false);
        validateString(recipient.getCity(), "City", CITY_MAX_LENGTH, false);
        validateString(recipient.getCategory(), "Category", CATEGORY_MAX_LENGTH, false);
    }

    /**
     * Validates a name, ensuring it contains only letters and spaces.
     * @param name The name to validate.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidName(String name) {
        return name != null && name.matches("[a-zA-Z ]+") && name.length() <= NAME_MAX_LENGTH;
    }

    /**
     * Validates a year, ensuring it is between 1900 and 2100.
     * @param year The year to validate.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidYear(int year) {
        return year >= 1900 && year <= 2100;
    }

    /**
     * Validates a city, ensuring it contains only letters and spaces.
     * @param city The city to validate.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidCity(String city) {
        return city != null && city.matches("[a-zA-Z ]+") && city.length() <= CITY_MAX_LENGTH;
    }

    /**
     * Validates a category, ensuring it contains only letters and spaces.
     * @param category The category to validate.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidCategory(String category) {
        return category != null && category.matches("[a-zA-Z ]+") && category.length() <= CATEGORY_MAX_LENGTH;
    }

    /**
     * Validates a string field for length and characters.
     * @param value the string value to validate
     * @param fieldName the name of the field
     * @param maxLength the maximum allowed length
     * @param isNullAllowed whether the field can be null
     * @throws ValidationException if validation fails
     */
    private static void validateString(String value, String fieldName, int maxLength, boolean isNullAllowed) throws ValidationException {
        if (value == null && isNullAllowed) {
            return;
        } else if (value == null && !isNullAllowed) {
            throw new ValidationException(String.format("%s cannot be null", fieldName));
        } else if (value.trim().isEmpty()) {
            throw new ValidationException(String.format("%s cannot be empty or only whitespace", fieldName));
        } else if (value.length() > maxLength) {
            throw new ValidationException(String.format("%s cannot exceed %d characters", fieldName, maxLength));
        } else if (!value.matches("[a-zA-Z ]+")) {  // Ensures only letters and spaces are allowed
            throw new ValidationException(String.format("%s must contain only letters", fieldName));
        }
    }
}
