/**
 * AddAuthorServlet.java
 *
 * Assignment 2 – CST8288_020_021  
 * Student Name: Saleha Qareen  
 * Student Number: 041161192  
 * Date: 03/25/2025
 *
 * Description:
 * Servlet to display a form to add a new author and handle the POST request to insert the author into the database.
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
 * Servlet handling adding a new author.
 * - Handles both GET (to show form) and POST (to process submission).
 */
public class AddAuthorServlet extends HttpServlet {

    // Business logic class used to interact with the DAO layer
    private final AuthorBusinessLogic logic = new AuthorBusinessLogic();

    /**
     * Handles HTTP GET requests and displays the Add Author form.
     *
     * @param req  HttpServletRequest object containing client request
     * @param resp HttpServletResponse object to send response back to client
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String errorMessage = req.getParameter("error");       // Get any error passed via query param
        String successMessage = req.getParameter("success");   // Get any success message passed

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            // HTML page + form
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Add Author</title>");
            out.println("<style>");
            out.println("body { background:#FAF3E0; font-family: Georgia; }");
            out.println(".container { width: 350px; margin: 100px auto; padding: 20px; text-align: center; background-color: #fff; border-radius: 10px; box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.2); }");
            out.println("label { display: block; margin: 8px 0 4px; }");
            out.println("input { width: 100%; padding: 6px; margin-bottom: 10px; }");
            out.println("button { background-color: #4CAF50; color: #fff; border: none; padding: 8px 15px; cursor: pointer; border-radius: 5px; }");
            out.println("button:hover { background-color: #45a049; }");
            out.println(".error { color: red; }");
            out.println(".success { color: green; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");

            out.println("<h1>Add Author</h1>");

            // Display any error or success message passed in the query
            if (errorMessage != null) {
                out.println("<p class='error'>" + errorMessage + "</p>");
            }
            if (successMessage != null) {
                out.println("<p class='success'>" + successMessage + "</p>");
            }

            // Form inputs
            out.println("<form action='AddAuthorServlet' method='post'>");
            out.println("<label>First Name:</label><input name='firstName'/><br/>");
            out.println("<label>Last Name:</label><input name='lastName'/><br/>");
            out.println("<button type='submit'>Add Author</button>");
            out.println("</form>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles HTTP POST requests and adds a new author to the database.
     *
     * @param req  HttpServletRequest object containing client data
     * @param resp HttpServletResponse object used to redirect or send errors
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Get user-submitted form fields
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        // Basic validation: fields should not be empty
        if (firstName == null || firstName.trim().isEmpty() ||
            lastName == null || lastName.trim().isEmpty()) {
            resp.sendRedirect("AddAuthorServlet?error=Fields+cannot+be+empty");
            return;
        }

        // Create an Author object (ID = 0 since it's auto-generated in DB)
        Author author = new Author(0, firstName.trim(), lastName.trim());

        try {
            // Call business logic to insert the new author
            logic.addAuthor(author);

            //  Redirect to show all authors with a success message
            resp.sendRedirect("Controller?action=getAllAuthors&success=Author+added+successfully!");
        } catch (DAOException e) {
            // If error during insert, send 500 error response
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
