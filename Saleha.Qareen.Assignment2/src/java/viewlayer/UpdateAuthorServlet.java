/**
 * UpdateAuthorServlet.java
 *
 * Assignment 2 – CST8288_020_021  
 * Student Name: Saleha Qareen  
 * Student Number: 041161192  
 * Date: 03/25/2025
 *
 * Description:
 * Servlet to display a form for updating an author and process the update operation upon form submission.
 */

package viewlayer;

import businesslayer.AuthorBusinessLogic;
import dataaccesslayer.DAOException;
import transferobjects.Author;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for handling author updates.
 * Displays a form to input updated author details on GET request,
 * and processes the update logic on POST request.
 */
public class UpdateAuthorServlet extends HttpServlet {

    // Business logic layer to interact with the DAO
    private final AuthorBusinessLogic logic = new AuthorBusinessLogic();

    /**
     * Handles GET requests by displaying the Update Author form.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException in case of Servlet issues
     * @throws IOException      in case of I/O errors
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String errorMessage = req.getParameter("error");
        String successMessage = req.getParameter("success");

        // Set response content type to HTML
        resp.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            // Generate HTML content for the form
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Update Author</title>");
            out.println("<style>");
            out.println("body { background:#FAF3E0; font-family: Georgia; display: flex; height: 100vh; align-items: center; justify-content: center; }");
            out.println(".container { background: #ffffff; width: 350px; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); text-align: center; }");
            out.println("h1 { margin-bottom: 20px; }");
            out.println("label { display: block; margin: 8px 0 4px; text-align: left; }");
            out.println("input { width: 100%; padding: 8px; margin-bottom: 10px; border: 1px solid #ccc; border-radius: 4px; }");
            out.println("button { background-color: #4CAF50; color: #fff; border: none; padding: 10px 20px; border-radius: 4px; cursor: pointer; }");
            out.println("button:hover { background-color: #45a049; }");
            out.println(".error { color: red; margin-bottom: 10px; }");
            out.println(".success { color: green; margin-bottom: 10px; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");

            out.println("<h1>Update Author</h1>");

            // Show error message if any
            if (errorMessage != null) {
                out.println("<p class='error'>" + errorMessage + "</p>");
            }

            // Show success message if any
            if (successMessage != null) {
                out.println("<p class='success'>" + successMessage + "</p>");
            }

            // Form for user input
            out.println("<form action='UpdateAuthorServlet' method='post'>");
            out.println("<label>Author ID:</label><input name='authorId' required/><br/>");
            out.println("<label>First Name:</label><input name='firstName' required/><br/>");
            out.println("<label>Last Name:</label><input name='lastName' required/><br/>");
            out.println("<button type='submit'>Update Author</button>");
            out.println("</form>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles POST requests to process the author update.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException in case of servlet issues
     * @throws IOException      in case of I/O errors
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Extract parameters from the form
        String authorIdParam = req.getParameter("authorId");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        // Basic validation for empty fields
        if (authorIdParam == null || authorIdParam.trim().isEmpty() ||
            firstName == null || firstName.trim().isEmpty() ||
            lastName == null || lastName.trim().isEmpty()) {
            resp.sendRedirect("UpdateAuthorServlet?error=All+fields+required");
            return;
        }

        try {
            // Convert authorId to integer
            int authorId = Integer.parseInt(authorIdParam);

            // Check if the author exists before updating
            if (!logic.authorExists(authorId)) {
                resp.sendRedirect("NotFoundServlet?error=Author+not+found+with+ID+" + authorId);
                return;
            }

            // Construct updated Author object
            Author author = new Author(authorId, firstName.trim(), lastName.trim());

            // Perform update via business logic layer
            logic.updateAuthor(author);

            // Redirect to main table with success message
            resp.sendRedirect("Controller?action=getAllAuthors&success=Author+updated+successfully!");

        } catch (NumberFormatException e) {
            // Handle invalid numeric input
            resp.sendRedirect("UpdateAuthorServlet?error=Invalid+Author+ID");
        } catch (DAOException e) {
            // Handle database-related exceptions
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
