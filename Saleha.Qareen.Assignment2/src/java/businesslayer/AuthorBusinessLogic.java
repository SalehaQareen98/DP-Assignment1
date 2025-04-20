/**
 * AuthorBusinessLogic.java
 *
 * Assignment 2 – CST8288_020_021  
 * Student Name: Saleha Qareen  
 * Student Number: 041161192  
 * Date: 03/25/2025
 *
 * Description:
 * Business logic layer that interacts with the DAO to perform operations on authors.
 * Acts as a bridge between the view and data access layers.
 */

package businesslayer;

import dataaccesslayer.AuthorDAO;
import dataaccesslayer.AuthorDAOImpl;
import dataaccesslayer.DAOException;
import transferobjects.Author;

import java.util.List;

/**
 * Business logic layer for handling Author-related operations.
 * This class acts as an intermediary between the servlets (view layer)
 * and the data access layer (DAO).
 */
public class AuthorBusinessLogic {

    // DAO instance to interact with the database
    private final AuthorDAO dao = new AuthorDAOImpl();

    /**
     * Fetches all authors from the database.
     * 
     * @return a list of all Author objects
     * @throws DAOException if a database access error occurs
     */
    public List<Author> getAllAuthors() throws DAOException {
        return dao.getAllAuthors();
    }

    /**
     * Retrieves a specific author by their ID.
     *
     * @param id the author ID to search for
     * @return the Author object if found, or null if not
     * @throws DAOException if a database access error occurs
     */
    public Author getAuthorById(int id) throws DAOException {
        return dao.getAuthorById(id);
    }

    /**
     * Adds a new author to the database.
     *
     * @param author the Author object containing first and last names
     * @throws DAOException if a database insert error occurs
     */
    public void addAuthor(Author author) throws DAOException {
        dao.addAuthor(author);
    }

    /**
     * Updates an existing author's information in the database.
     *
     * @param author the Author object with updated fields
     * @throws DAOException if the author ID doesn't exist or update fails
     */
    public void updateAuthor(Author author) throws DAOException {
        dao.updateAuthor(author);
    }

    /**
     * Deletes an author by their ID from the database.
     *
     * @param id the ID of the author to delete
     * @throws DAOException if no matching author is found or deletion fails
     */
    public void deleteAuthor(int id) throws DAOException {
        dao.deleteAuthor(id);
    }

    /**
     * Checks if an author exists in the database by ID.
     *
     * @param authorId the ID to check
     * @return true if the author exists, false otherwise
     * @throws DAOException if a database access error occurs
     */
    public boolean authorExists(int authorId) throws DAOException {
        return dao.getAuthorById(authorId) != null;
    }
}
