package ua.nure.ponomarev.web.command.registration;

import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.exception.LogicException;
import ua.nure.ponomarev.exception.MailSenderException;
import ua.nure.ponomarev.service.NotificationService;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.web.command.FrontCommand;
import ua.nure.ponomarev.web.handler.ExceptionHandler;

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
public class MailConfirmCommand extends FrontCommand {
    private UserService userService;
    private NotificationService notificationService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response,  servletContext, config);
        userService = (UserService) config.getServletContext().getAttribute("user_service");
        notificationService = (NotificationService) config.getServletContext().getAttribute("notificator");
    }

    @Override
    public void execute() throws ServletException, IOException {
        String email;
        int id = Integer.parseInt(request.getParameter("id"));
        String userIdLine = (String) request.getSession().getAttribute("userId");
        if (userIdLine != null) {
            int userId = Integer.parseInt(userIdLine);
            if (notificationService.isValidEmailId(id)) {
                email = notificationService.removeEmailId(id);
                try {
                    if (userService.putEmail(email, userId)) {
                      forward("");//CONGRAT PAGE
                    } else {
                        ExceptionHandler.handleException(new MailSenderException("Email has already been activated or deleted", LogicException.ExceptionType.USER_EXCEPTION), request, response);
                    }
                } catch (DBException e) {
                    ExceptionHandler.handleException(e, request, response);
                }
            }
        }
    }
}
