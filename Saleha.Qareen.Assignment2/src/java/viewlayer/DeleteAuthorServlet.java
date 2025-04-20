/**
 * DeleteAuthorServlet.java
 *
 * Assignment 2 – CST8288_020_021  
 * Student Name: Saleha Qareen  
 * Student Number: 041161192  
 * Date: 03/25/2025
 *
 * Description:
 * Servlet to display a form for deleting an author by ID and handle the deletion logic.
 */

package viewlayer;

import businesslayer.AuthorBusinessLogic;
import dataaccesslayer.DAOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for deleting an author by ID.
 * - GET shows the form to enter ID
 * - POST deletes the author if ID is valid and exists
 */
public class DeleteAuthorServlet extends HttpServlet {

    // Business layer used to handle author operations
    private final AuthorBusinessLogic logic = new AuthorBusinessLogic();

    /**
     * Central handler for both GET and POST requests.
     *
     * @param req  The incoming HttpServletRequest
     * @param resp The HttpServletResponse to return content
     */
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String authorIdParam = req.getParameter("authorId"); // Get ID from form or URL
        String errorMessage = req.getParameter("error");     // Optional error message

        //  Show form if no ID is submitted (GET)
        if (authorIdParam == null) {
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                // HTML UI for deletion form
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Delete Author</title>");
                out.println("<style>");
                out.println("body {background:#FAF3E0; font-family:Georgia;}");
                out.println(".container {width:400px; margin:80px auto; padding:20px; background:#fff; border:1px solid #ddd; border-radius:8px; text-align:center;}");
                out.println("label {display:block; margin-bottom:5px;}");
                out.println("input {width:100%; padding:8px; margin-bottom:15px;}");
                out.println(".error {color:red; margin-bottom:15px;}");
                out.println(".success {color:green; margin-bottom:15px;}");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='container'>");
                out.println("<h1>Delete Author</h1>");

                // Show error if passed
                if (errorMessage != null) {
                    out.println("<p class='error'>" + errorMessage + "</p>");
                }

                // Form to input Author ID
                out.println("<form action='DeleteAuthorServlet' method='post'>");
                out.println("<label>Author ID:</label>");
                out.println("<input name='authorId' placeholder='Enter Author ID'/>");
                out.println("<button type='submit'>Delete Author</button>");
                out.println("</form>");

                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }
            return;
        }

        //If authorId was submitted, validate it
        if (authorIdParam.trim().isEmpty()) {
            // Redirect with error if empty
            resp.sendRedirect("DeleteAuthorServlet?error=Author+ID+is+required");
            return;
        }

        try {
            int authorId = Integer.parseInt(authorIdParam);

            // Check if author exists before deleting
            if (!logic.authorExists(authorId)) {
                // If not found, redirect to a NotFoundServlet
                resp.sendRedirect("NotFoundServlet?error=Author+not+found+with+ID+" + authorId);
                return;
            }

            // Delete the author
            logic.deleteAuthor(authorId);

            //  On success, redirect to main table with success message
            resp.sendRedirect("Controller?action=getAllAuthors&success=Author+deleted+successfully");

        } catch (NumberFormatException e) {
            // Handle case where input is not a valid integer
            resp.sendRedirect("DeleteAuthorServlet?error=Invalid+Author+ID");

        } catch (DAOException e) {
            // Handle database access error
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * Handle GET request (typically shows the form).
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Handle POST request (typically processes deletion).
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }
}
