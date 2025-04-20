/**
 * GetAllAuthorsServlet.java
 *
 * Assignment 2 – CST8288_020_021  
 * Student Name: Saleha Qareen  
 * Student Number: 041161192  
 * Date: 03/25/2025
 *
 * Description:
 * Servlet to retrieve and display all authors from the database in a styled HTML table.
 */

package viewlayer;

import businesslayer.AuthorBusinessLogic;
import dataaccesslayer.DAOException;
import transferobjects.Author;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet to handle displaying all authors.
 * It fetches author records from the database using the business layer
 * and renders them in an HTML table.
 */
public class GetAllAuthorsServlet extends HttpServlet {

    // Handles author-related operations from business logic
    private final AuthorBusinessLogic logic = new AuthorBusinessLogic();

    /**
     * Shared method that handles both GET and POST requests.
     * It queries all authors and renders them in a table.
     */
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Set content type to HTML with UTF-8 encoding
        resp.setContentType("text/html;charset=UTF-8");

        // Get optional success or error messages from request
        String successMessage = req.getParameter("success");
        String errorMessage = req.getParameter("error");

        try (PrintWriter out = resp.getWriter()) {
            // Fetch the list of all authors
            List<Author> authors = logic.getAllAuthors();

            // Begin HTML response
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>All Authors</title>");
            out.println("<style>");
            out.println("body { background:#FAF3E0; font-family: Georgia; }");
            out.println(".container { text-align: center; margin-top: 50px; }");
            out.println(".success { color: green; }");
            out.println(".error { color: red; }");
            out.println("table { border-collapse: collapse; width: 60%; margin: auto; }");
            out.println("th, td { border: 1px solid black; padding: 8px; text-align: center; }");
            out.println("a { display: block; margin-top: 20px; text-align: center; text-decoration: none; color: #4CAF50; }");
            out.println("a:hover { color: #45a049; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");

            // Title
            out.println("<h1>All Authors</h1>");

            // Show success message (e.g., after add/delete/update)
            if (successMessage != null) {
                out.println("<p class='success'>" + successMessage + "</p>");
            }

            // Show error message (e.g., if query failed)
            if (errorMessage != null) {
                out.println("<p class='error'>" + errorMessage + "</p>");
            }

            // Display table with author data
            out.println("<table>");
            out.println("<tr><th>ID</th><th>First</th><th>Last</th></tr>");
            for (Author a : authors) {
                out.println("<tr><td>" + a.getAuthorId() + "</td><td>" 
                           + a.getFirstName() + "</td><td>" 
                           + a.getLastName() + "</td></tr>");
            }
            out.println("</table>");

            // Link to go back to the main menu
            out.println("<a href='Controller?action=mainMenu'>Back to Main Menu</a>");

            // Close container and body
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (DAOException e) {
            // Redirect back to this page with a generic error message
            resp.sendRedirect("Controller?action=getAllAuthors&error=Error+fetching+authors");
        }
    }

    /**
     * Handles GET requests to show the authors list.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Handles POST requests (optional, also calls processRequest).
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }
}
