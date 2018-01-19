package ua.nure.ponomarev.web.command.registration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.entity.User;
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
import java.io.PrintWriter;

/**
 * @author Bogdan_Ponamarev.
 */
public class PinCodeCheckCommand extends FrontCommand {
    private static final Logger logger = LogManager.getLogger(PinCodeCheckCommand.class);
    private NotificationService notificationService;
    private UserService userService;


    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response,  servletContext, config);
        userService = (UserService) config.getServletContext().getAttribute("user_service");
        notificationService = (NotificationService) config.getServletContext().getAttribute("notification_service");
    }

    @Override
    public void execute() throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            if (!request.getParameter("pin_code").chars().allMatch(Character::isDigit)
                    || !notificationService.isValidPhonePinCode(Integer.parseInt(request.getParameter("pin_code"))
                    , request.getParameter("phone_number"))) {
                 response.sendError(500);
                logger.info("Incorrect ajax data");
            }
            out.close();
        }
    }
}
