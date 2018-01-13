package ua.nure.ponomarev.web.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.service.NotificationService;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.web.handler.ExceptionHandler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Bogdan_Ponamarev.
 */
@WebServlet(name = "PhoneConfirmServlet", urlPatterns = "/phone_confirm")
public class PhoneConfirmServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(PhoneConfirmServlet.class);
    private NotificationService notificationService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) config.getServletContext().getAttribute("user_service");
        notificationService = (NotificationService) config.getServletContext().getAttribute("notification_service");
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (request.getParameter("pin_code").chars().allMatch(Character::isDigit)
                    && notificationService.isValidPhonePinCode(Integer.parseInt(request.getParameter("pin_code"))
                    , request.getParameter("phone_number"))) {
                out.print("yes");
                User user = (User)request.getSession().getAttribute("user");
                if(user!=null){
                    try {
                        userService.addUser(user);
                        logger.info("Correct ajax data");
                    } catch (DBException e) {
                        ExceptionHandler.handleException(e,request,response);
                    }
                }
                logger.error("There is no user in session");
            } else {
                out.print("no");
                logger.info("Incorrect ajax data");
            }
            out.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            logger.error("Ajax request does not work", ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            logger.error("Ajax request does not work", ex);
        }
    }
}
