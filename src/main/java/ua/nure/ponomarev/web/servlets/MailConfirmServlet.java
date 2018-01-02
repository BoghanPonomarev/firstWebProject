package ua.nure.ponomarev.web.servlets;

import ua.nure.ponomarev.service.NotificationService;
import ua.nure.ponomarev.web.exception.DBException;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.web.exception.LogicException;
import ua.nure.ponomarev.web.exception.MailException;
import ua.nure.ponomarev.web.handler.ExceptionHandler;
import ua.nure.ponomarev.notification.mail_notification.MailNotificator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bogdan_Ponamarev.
 */
@WebServlet(name = "MailConfirmServlet", urlPatterns = "/confirm_registration")
public class MailConfirmServlet extends HttpServlet {
    private UserService userService;
    private NotificationService notificationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) config.getServletContext().getAttribute("user_service");
        notificationService = (NotificationService) config.getServletContext().getAttribute("notificator");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = "";
        int id = Integer.parseInt(request.getParameter("id"));
        if (notificationService.isValidEmailId(id)) {
            email = notificationService.removeEmailId(id);
            try {
                if (userService.isExistEmail(email)) {
                    if (userService.activateEmail(email)) {
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("");//--CONGRATULATION JSP
                        requestDispatcher.forward(request, response);
                    } else {
                    ExceptionHandler.handleException(new MailException("Email has already been activated", LogicException.ExceptionType.USER_EXCEPTION),request,response);
                    }
                    ExceptionHandler.handleException(new MailException("Email was deleted or changed", LogicException.ExceptionType.USER_EXCEPTION),request,response);
                }
            } catch (DBException e) {
                ExceptionHandler.handleException(e,request,response);
            }
        }
    }
}
