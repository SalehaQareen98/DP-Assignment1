/**
 * FrontController.java
 *
 * Assignment 2 – CST8288_020_021  
 * Student Name: Saleha Qareen  
 * Student Number: 041161192  
 * Date: 03/25/2025
 *
 * Description:
 * Central controller servlet that routes all incoming requests based on the 'action' parameter.
 * Delegates requests to appropriate servlets and handles login and main menu display.
 */

package viewlayer;

import businesslayer.AuthorBusinessLogic;
import dataaccesslayer.DataSource;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * FrontController servlet that handles all incoming requests
 * and dispatches them to appropriate handlers or servlets based on the 'action' parameter.
 */
public class FrontController extends HttpServlet {

    private final AuthorBusinessLogic logic = new AuthorBusinessLogic();

    /**
     * Handles HTTP GET requests.
     * @param req
     * @param resp
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Handles HTTP POST requests.
     * @param req
     * @param resp
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Processes incoming requests and dispatches them to appropriate handlers or servlets.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws IOException      if an input/output error occurs
     * @throws ServletException if a Servlet error occurs
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");

        // Default behavior - Show Main Menu if no action specified
        if (action == null) {
            action = "mainMenu";
        }

        switch (action) {
            case "loginPage" -> showLoginPage(resp);

            case "authenticate" -> handleAuthenticate(req, resp);

            case "mainMenu" -> showMainMenu(resp);  // Show Main Menu directly

            case "getAllAuthors" -> req.getRequestDispatcher("/GetAllAuthorsServlet").forward(req, resp);

            case "showGetAuthorByIdForm" -> {
                req.setAttribute("error", req.getParameter("error"));
                req.getRequestDispatcher("/GetAuthorByIdServlet").forward(req, resp);
            }

            case "getAuthorById" -> req.getRequestDispatcher("/GetAuthorByIdServlet").forward(req, resp);

            case "addAuthor" -> req.getRequestDispatcher("/AddAuthorServlet").forward(req, resp);

            case "updateAuthor" -> req.getRequestDispatcher("/UpdateAuthorServlet").forward(req, resp);

            case "deleteAuthor" -> req.getRequestDispatcher("/DeleteAuthorServlet").forward(req, resp);

            default -> {
                req.setAttribute("error", "Unknown action: " + action);
                req.getRequestDispatcher("/NotFound-URL").include(req, resp);
            }
        }
    }

    /**
     * Displays the login page with username and password fields.
     *
     * @param resp the HttpServletResponse object
     * @throws IOException if an input/output error occurs
     */
    private void showLoginPage(HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Enter DBMS Credentials</title></head>");
            out.println("<body style='background:#FAF3E0; font-family:Georgia;'>");
            out.println("<div style='width:400px; margin:80px auto; text-align:center;'>");
            out.println("<h1>Enter DBMS Credentials</h1>");
            out.println("<form action='Controller' method='post'>");
            out.println("<label>Username:</label><input name='dbUser'/><br/>");
            out.println("<label>Password:</label><input type='password' name='dbPass'/><br/>");
            out.println("<input type='hidden' name='action' value='authenticate'/>");
            out.println("<button type='submit'>Login</button>");
            out.println("</form>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles authentication logic and redirects to the main menu on success.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws IOException if an input/output error occurs
     */
    private void handleAuthenticate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("dbUser");
        String password = req.getParameter("dbPass");

        DataSource.getInstance().setCredentials(username, password);

        try (Connection conn = DataSource.getInstance().createConnection()) {
            resp.sendRedirect("Controller?action=mainMenu");  // Redirect to Main Menu
        } catch (SQLException e) {
            showLoginPageWithError(resp, "Invalid username or password. Please try again.");
        }
    }

    /**
     * Displays the main menu with action buttons for each CRUD operation.
     *
     * @param resp the HttpServletResponse object
     * @throws IOException if an input/output error occurs
     */
    private void showMainMenu(HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Main Menu</title></head>");
            out.println("<body style='background:#FAF3E0; font-family:Georgia;'>");
            out.println("<div class='container'>");
            out.println("<h1 style='color:green;'>Login successful!</h1>");
            generateActionButtons(out);
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Displays the login page with an error message.
     *
     * @param resp         the HttpServletResponse object
     * @param errorMessage the error message to display
     * @throws IOException if an input/output error occurs
     */
    private void showLoginPageWithError(HttpServletResponse resp, String errorMessage) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Login Failed</title></head>");
            out.println("<body>");
            out.println("<h1 style='color:red;'>" + errorMessage + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Generates action buttons to perform CRUD operations on authors.
     *
     * @param out the PrintWriter to write the HTML buttons
     */
    private void generateActionButtons(PrintWriter out) {
        out.println("<div class='buttons'>");
        out.println("<form action='Controller' method='get' style='display:inline;'>");
        out.println("<button name='action' value='getAllAuthors'>GetAllAuthors</button></form>");
        out.println("<form action='Controller' method='get' style='display:inline;'>");
        out.println("<button name='action' value='showGetAuthorByIdForm'>GetAuthorByAuthorId</button></form>");
        out.println("<form action='Controller' method='get' style='display:inline;'>");
        out.println("<button name='action' value='addAuthor'>AddAuthor</button></form>");
        out.println("<form action='Controller' method='get' style='display:inline;'>");
        out.println("<button name='action' value='updateAuthor'>UpdateAuthorById</button></form>");
        out.println("<form action='Controller' method='get' style='display:inline;'>");
        out.println("<button name='action' value='deleteAuthor'>DeleteAuthorById</button></form>");
        out.println("</div>");
    }
}
