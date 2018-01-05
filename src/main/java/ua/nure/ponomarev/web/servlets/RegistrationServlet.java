package ua.nure.ponomarev.web.servlets;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.exception.SMSSenderException;
import ua.nure.ponomarev.service.NotificationService;
import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.web.form.FormMaker;
import ua.nure.ponomarev.web.form.RegistrationForm;
import ua.nure.ponomarev.web.handler.ExceptionHandler;
import ua.nure.ponomarev.web.transformer.UserTransformer;
import ua.nure.ponomarev.web.validator.RegistrationValidator;
import ua.nure.ponomarev.web.validator.Validator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bogdan_Ponamarev.
 */
@WebServlet(name = "RegistrationServlet", urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(RegistrationServlet.class);
    private FormMaker abstractFormMaker;
    private RegistrationValidator formValidator;
    private UserService userService;
    private NotificationService notificationService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        formValidator = (RegistrationValidator) config.getServletContext().getAttribute("registration_validator");
        abstractFormMaker = (FormMaker) config.getServletContext().getAttribute("form_maker");
        userService = (UserService) config.getServletContext().getAttribute("user_service");
        notificationService = (NotificationService) config.getServletContext().getAttribute("notification_service");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RegistrationForm registrationForm = abstractFormMaker.createRegistrationForm(request);
        Map<String, String> errors = formValidator.validate(registrationForm);
        try {
            if (userService.isExistEmail(registrationForm.getEmail())) {
                errors.put("emailExist", "Email has already taken");
            }
            if (userService.isExistPhoneNumber(registrationForm.getPhoneNumber())) {
                errors.put("phoneNumberExist", "Phone number has already taken");
            }
        } catch (DBException e) {
            ExceptionHandler.handleException(e,request,response);
        }
        if (!errors.isEmpty()) {
            logger.info("User made some registration mistakes");
            request.setAttribute("errors", errors);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/jsp/registration.jsp");
            requestDispatcher.forward(request, response);
        }
        else{
            logger.info("User entered correct data");
            request.getSession().setAttribute("user",UserTransformer.transform(registrationForm));
            request.setAttribute("asd","asd");
            request.setAttribute("phone_number",request.getParameter("phone_number"));
            new Thread(){
                @Override
                       public void run(){
                    try {
                        notificationService.sendPinCode(request.getParameter("phone_number"));
                    } catch (SMSSenderException e) {
                        try {
                            ExceptionHandler.handleException(e, request, response);
                        } catch (Exception e1) {
                           logger.error("Phone sender ex was not handled "+e1);
                        }
                    }
                }
            }.start();

            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/jsp/phone_confirm.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/jsp/registration.jsp");
        requestDispatcher.forward(request, response);
    }
}