package ua.nure.ponomarev.web.command.user.data_edition;

import ua.nure.ponomarev.exception.MailSenderException;
import ua.nure.ponomarev.service.NotificationService;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.web.command.FrontCommand;
import ua.nure.ponomarev.web.handler.ExceptionHandler;
import ua.nure.ponomarev.web.page.Mapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bogdan_Ponamarev.
 */
public class MailSendCommand extends FrontCommand {
    private UserService userService;
    private NotificationService notificationService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        userService = (UserService) config.getServletContext().getAttribute("user_service");
        notificationService = (NotificationService) config.getServletContext().getAttribute("notificator");
    }
    @Override
    public void execute() throws ServletException, IOException {
        String email = request.getParameter("email");
        try {
            notificationService.sendConfirmEmail(email);
        } catch (MailSenderException e) {
            ExceptionHandler.handleException(e,request,response);
        }
        forward(Mapping.getPagePath(Mapping.Page.USER_SUCCESS_EMAIL_SENDING_PAGE));
    }
}
