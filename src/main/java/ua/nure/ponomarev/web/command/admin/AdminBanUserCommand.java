package ua.nure.ponomarev.web.command.admin;

import ua.nure.ponomarev.exception.DbException;
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
public class AdminBanUserCommand extends FrontCommand {
    private UserService userService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        userService = (UserService) config.getServletContext().getAttribute("user_service");
    }

    @Override
    public void execute() throws ServletException, IOException {
        String id = request.getParameter("userId");
        try {
            userService.setBanValue(Integer.parseInt(id), !Boolean.parseBoolean(request.getParameter("banStatus")));
        } catch (DbException e) {
            ExceptionHandler.handleException(e, request, response);
        }
        redirect(servletContext.getContextPath()+"/user/profile?userId="+id);
    }
}
