/**
 * Author.java
 *
 * Assignment 2 – CST8288_020_021  
 * Student Name: Saleha Qareen  
 * Student Number: 041161192  
 * Date: 03/25/2025
 *
 * Description:
 * Transfer object representing a row in the 'authors' table.
 * It encapsulates the author ID, first name, and last name with appropriate getters and setters.
 */

package transferobjects;

/**
 * Transfer object representing a row in the authors table.
 * Encapsulates data for an author including ID, first name, and last name.
 */
public class Author {
    private int authorId;       // Unique identifier for the author (Primary Key)
    private String firstName;   // First name of the author
    private String lastName;    // Last name of the author

    /**
     * Default constructor.
     */
    public Author() { }

    /**
     * Constructs an Author object with all fields initialized.
     *
     * @param authorId   the ID of the author
     * @param firstName  the first name of the author
     * @param lastName   the last name of the author
     */
    public Author(int authorId, String firstName, String lastName) {
        this.authorId = authorId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Returns the author's ID.
     *
     * @return author ID
     */
    public int getAuthorId() {
        return authorId;
    }

    /**
     * Sets the author's ID.
     *
     * @param authorId the ID to set
     */
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    /**
     * Returns the author's first name.
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the author's first name.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the author's last name.
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the author's last name.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns a string representation of the author.
     *
     * @return formatted string "ID: FirstName LastName"
     */
    @Override
    public String toString() {
        return authorId + ": " + firstName + " " + lastName;
    }
}
