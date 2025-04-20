/**
 * AuthorDAO.java
 *
 * Assignment 2 – CST8288_020_021  
 * Student Name: Saleha Qareen  
 * Student Number: 041161192  
 * Date: 03/25/2025
 *
 * Description:
 * DAO interface that defines CRUD operations for the 'authors' table.
 * This is implemented by AuthorDAOImpl.
 */

package dataaccesslayer;

import transferobjects.Author;
import java.util.List;

/**
 * AuthorDAO is a Data Access Object (DAO) interface that defines
 * basic CRUD operations (Create, Read, Update, Delete) for the authors table.
 * 
 * All implementing classes must provide behavior to interact with the database
 * and handle any SQL exceptions by throwing DAOException.
 */
public interface AuthorDAO {

    /**
     * Retrieves all author records from the database.
     *
     * @return a List of all Author objects in the authors table.
     * @throws DAOException if a database access error occurs.
     */
    List<Author> getAllAuthors() throws DAOException;

    /**
     * Retrieves a single Author by their unique ID.
     *
     * @param authorId the ID of the author to fetch.
     * @return the Author object if found, or null if no author with the given ID exists.
     * @throws DAOException if a database access error occurs.
     */
    Author getAuthorById(int authorId) throws DAOException;

    /**
     * Inserts a new Author into the database.
     *
     * @param author the Author object to add (ID is typically auto-generated).
     * @throws DAOException if a database access error occurs.
     */
    void addAuthor(Author author) throws DAOException;

    /**
     * Updates an existing Author's first and last name based on their ID.
     *
     * @param author the Author object containing updated values.
     * @throws DAOException if the update fails or the author is not found.
     */
    void updateAuthor(Author author) throws DAOException;

    /**
     * Deletes an author from the database using their ID.
     *
     * @param authorId the ID of the author to delete.
     * @throws DAOException if the deletion fails or no author is found with the given ID.
     */
    void deleteAuthor(int authorId) throws DAOException;
}
