package ua.nure.ponomarev.web.command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bogdan_Ponamarev.
 */
public abstract class FrontCommand {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected ServletContext servletContext;

    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        this.request = request;
        this.response = response;
        this.servletContext = servletContext;
    }

    public abstract void execute() throws ServletException, IOException;

    public void forward(String path) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    public void redirect(String path) throws IOException {
        response.sendRedirect(path);
    }

}
