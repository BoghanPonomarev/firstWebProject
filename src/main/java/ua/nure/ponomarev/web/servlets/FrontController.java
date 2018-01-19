package ua.nure.ponomarev.web.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.web.command.FrontCommand;
import ua.nure.ponomarev.web.page.Mapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bogdan_Ponamarev.
 */
@WebServlet(name = "FrontController",urlPatterns = "/")
public class FrontController extends HttpServlet {
    private static Logger logger= LogManager.getLogger(FrontController.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            FrontCommand command = makeCommand(request,response);
            if(command!=null){
                command.execute();
            }else {
                response.sendError(404);
            }
        } catch (Exception e) {
            logger.error("Command class can`t be created "+e);
            response.sendError(404);
        }
    }

    private FrontCommand makeCommand(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InstantiationException {
        String contextPath=request.getContextPath();
        String url = request.getRequestURI();
        url = url.substring(url.indexOf(contextPath)+contextPath.length()+1);
        Class commandClass = Mapping.getCommand(url);
        FrontCommand command = (FrontCommand)commandClass.newInstance();
        command.init(request,response,getServletContext(),getServletConfig());
        return command;
    }
}
