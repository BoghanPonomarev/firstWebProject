package ua.nure.ponomarev.web.command.registration;

import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.service.NotificationService;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.web.command.FrontCommand;
import ua.nure.ponomarev.web.handler.ExceptionHandler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bogdan_Ponamarev.
 */
public class CheckIsExistPhoneCommand extends FrontCommand {

    private UserService userService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        userService = (UserService) config.getServletContext().getAttribute("user_service");
    }
    @Override
    public void execute() throws ServletException, IOException {
        String phoneNumber = request.getParameter("phone_number");
        try {
            if(phoneNumber==null||userService.isExistPhoneNumber(phoneNumber)){
                response.sendError(500);
            }
        } catch (DBException e) {
            ExceptionHandler.handleException(e,request,response);
        }
        response.getWriter().close();
    }
}
