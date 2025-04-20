/**
 * AuthorDAOImpl.java
 *
 * Assignment 2 – CST8288_020_021  
 * Student Name: Saleha Qareen  
 * Student Number: 041161192  
 * Date: 03/25/2025
 *
 * Description:
 * Implements the AuthorDAO interface.
 * Provides database logic for CRUD operations on the 'authors' table using JDBC.
 */

package dataaccesslayer;

import transferobjects.Author;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of AuthorDAO interface that performs actual
 * CRUD operations on the authors table using JDBC.
 */
public class AuthorDAOImpl implements AuthorDAO {

    /**
     * Retrieves all authors from the database.
     *
     * @return a list of Author objects.
     * @throws DAOException if a database error occurs.
     */
    @Override
    public List<Author> getAllAuthors() throws DAOException {
        String sql = "SELECT authorID, firstName, lastName FROM authors";

        // Try-with-resources for connection, statement, and result set
        try (Connection conn = DataSource.getInstance().createConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Author> list = new ArrayList<>();

            // Iterate over result set and build Author list
            while (rs.next()) {
                list.add(new Author(
                    rs.getInt("authorID"),
                    rs.getString("firstName"),
                    rs.getString("lastName")
                ));
            }

            return list;

        } catch (SQLException e) {
            throw new DAOException("Error fetching all authors", e);
        }
    }

    /**
     * Retrieves a single author by their ID.
     *
     * @param authorId the ID of the author to retrieve.
     * @return the Author object or null if not found.
     * @throws DAOException if a database error occurs.
     */
    @Override
    public Author getAuthorById(int authorId) throws DAOException {
        String sql = "SELECT authorID, firstName, lastName FROM authors WHERE authorID = ?";

        try (Connection conn = DataSource.getInstance().createConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, authorId);

            // Execute query and retrieve single author
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                    ? new Author(rs.getInt("authorID"), rs.getString("firstName"), rs.getString("lastName"))
                    : null;
            }

        } catch (SQLException e) {
            throw new DAOException("Error fetching author by ID", e);
        }
    }

    /**
     * Inserts a new author into the database.
     *
     * @param author the Author object to insert.
     * @throws DAOException if a database error occurs.
     */
    @Override
    public void addAuthor(Author author) throws DAOException {
        String sql = "INSERT INTO authors (firstName, lastName) VALUES (?, ?)";

        try (Connection conn = DataSource.getInstance().createConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set values for insertion
            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error adding author", e);
        }
    }

    /**
     * Updates an existing author's name fields based on their ID.
     *
     * @param author the Author object with updated info.
     * @throws DAOException if update fails or author is not found.
     */
    @Override
    public void updateAuthor(Author author) throws DAOException {
        String sql = "UPDATE authors SET firstName = ?, lastName = ? WHERE authorID = ?";

        try (Connection conn = DataSource.getInstance().createConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set parameters for update
            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            ps.setInt(3, author.getAuthorId());

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                throw new DAOException("No author found with ID " + author.getAuthorId());
            }

        } catch (SQLException e) {
            throw new DAOException("Error updating author", e);
        }
    }

    /**
     * Deletes an author from the database by ID.
     *
     * @param authorId the ID of the author to delete.
     * @throws DAOException if deletion fails or no such author exists.
     */
    @Override
    public void deleteAuthor(int authorId) throws DAOException {
        String sql = "DELETE FROM authors WHERE authorID = ?";

        try (Connection conn = DataSource.getInstance().createConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, authorId);

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted == 0) {
                throw new DAOException("No author found with ID " + authorId);
            }

        } catch (SQLException e) {
            throw new DAOException("Error deleting author", e);
        }
    }

}
