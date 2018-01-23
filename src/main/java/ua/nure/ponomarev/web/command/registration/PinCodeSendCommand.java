package ua.nure.ponomarev.web.command.registration;

import ua.nure.ponomarev.exception.SmsSenderException;
import ua.nure.ponomarev.service.NotificationService;
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
public class PinCodeSendCommand extends FrontCommand {
    private NotificationService notificationService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        notificationService = (NotificationService) config.getServletContext().getAttribute("notification_service");
    }

    @Override
    public void execute() throws ServletException, IOException {
        try {
            notificationService.sendPinCode(request.getParameter("phone_number"));
        } catch (SmsSenderException e) {
            ExceptionHandler.handleException(e, request, response);
        }
    }
}
