/**
 * GetAuthorByIdServlet.java
 *
 * Assignment 2 – CST8288_020_021  
 * Student Name: Saleha Qareen  
 * Student Number: 041161192  
 * Date: 03/25/2025
 *
 * Description:
 * Servlet that displays a form to input an author ID and retrieves the corresponding author details.
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
 * Servlet for retrieving author details by ID.
 * It displays a form for entering the Author ID, and if provided, shows the author details.
 */
public class GetAuthorByIdServlet extends HttpServlet {

    // Business logic layer to interact with DAO
    private final AuthorBusinessLogic logic = new AuthorBusinessLogic();

    /**
     * Handles GET requests.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Handles POST requests.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Common method for handling both GET and POST requests.
     * Renders the form, processes input, and displays author details if found.
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        // Get the Author ID from the request
        String authorIdParam = req.getParameter("authorId");
        String errorMessage = req.getParameter("error");

        try (PrintWriter out = resp.getWriter()) {
            // Start HTML output
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Get Author By ID</title>");
            out.println("<style>");
            out.println("body { background:#FAF3E0; font-family: Georgia; }");
            out.println(".container { width: 350px; margin: 100px auto; padding: 20px; background: #fff; ");
            out.println("box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); border-radius: 8px; text-align: center; }");
            out.println("label { display: block; margin: 8px 0 4px; text-align: left; }");
            out.println("input { width: 100%; padding: 6px; margin-bottom: 10px; }");
            out.println("button { background-color: #4CAF50; color: #fff; border: none; padding: 8px 15px; cursor: pointer; width: 100%; }");
            out.println("button:hover { background-color: #45a049; }");
            out.println(".error { color: red; }");
            out.println(".details { text-align: left; margin-top: 15px; border-top: 1px solid #ddd; padding-top: 10px; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");

            out.println("<h1>Get Author By ID</h1>");

            // Display any error messages passed via query parameter
            if (errorMessage != null) {
                out.println("<p class='error'>" + errorMessage + "</p>");
            }

            // Display the search form
            out.println("<form action='Controller' method='get'>");
            out.println("<label>Author ID:</label><input name='authorId'/><br/>");
            out.println("<input type='hidden' name='action' value='getAuthorById'/>");
            out.println("<button type='submit'>Search</button>");
            out.println("</form>");

            // If an Author ID was provided, try to fetch and show the author
            if (authorIdParam != null && !authorIdParam.trim().isEmpty()) {
                try {
                    int authorId = Integer.parseInt(authorIdParam);
                    Author author = logic.getAuthorById(authorId);

                    if (author != null) {
                        // Display author details if found
                        out.println("<div class='details'>");
                        out.println("<h2>Author Details</h2>");
                        out.println("<p>ID: " + author.getAuthorId() + "</p>");
                        out.println("<p>First Name: " + author.getFirstName() + "</p>");
                        out.println("<p>Last Name: " + author.getLastName() + "</p>");
                        out.println("</div>");
                    } else {
                        // Redirect to NotFoundServlet if no author found
                        resp.sendRedirect("NotFoundServlet?error=Author+ID+" + authorId + "+not+found");
                    }
                } catch (NumberFormatException e) {
                    // Redirect if ID format is invalid
                    resp.sendRedirect("NotFoundServlet?error=Invalid+Author+ID");
                } catch (DAOException e) {
                    // Display DAO-related errors
                    out.println("<p class='error'>Error fetching author: " + e.getMessage() + "</p>");
                }
            }

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
