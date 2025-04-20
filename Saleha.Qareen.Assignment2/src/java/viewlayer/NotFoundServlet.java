/**
 * NotFoundServlet.java
 *
 * Assignment 2 – CST8288_020_021  
 * Student Name: Saleha Qareen  
 * Student Number: 041161192  
 * Date: 03/25/2025
 *
 * Description:
 * Servlet to display a user-friendly message when an author record is not found or an invalid request is made.
 */

package viewlayer;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for displaying a generic "Record Not Found" error page.
 * Used when a lookup by ID fails or invalid input is provided.
 */
public class NotFoundServlet extends HttpServlet {

    /**
     * Handles GET requests and renders a user-friendly error page.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if a Servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Retrieve any custom error message passed via query parameter
        String errorMessage = req.getParameter("error");

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            // Begin HTML output
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Record Not Found</title>");
            out.println("<style>");
            out.println("body { background:#FAF3E0; text-align:center; padding-top: 100px; font-family:Georgia; }");
            out.println("h1 { color: red; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Record Not Found</h1>");

            // Display dynamic error message if present
            if (errorMessage != null) {
                out.println("<p>" + errorMessage + "</p>");
            }

            // Link back to the list of all authors
            out.println("<a href='Controller?action=getAllAuthors'>Back to Author List</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
