package ua.nure.ponomarev.web.servlets;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.web.exception.DBException;
import ua.nure.ponomarev.service.api.UserService;
import ua.nure.ponomarev.web.form.AbstractFormMaker;
import ua.nure.ponomarev.web.form.RegistrationForm;
import ua.nure.ponomarev.web.handler.ExceptionHandler;
import ua.nure.ponomarev.web.mail.MailSender;
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
    private AbstractFormMaker abstractFormMaker;
    private Validator<RegistrationForm> formValidator;
    private UserService userService;
    private MailSender mailSender;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        formValidator = (Validator<RegistrationForm>) config.getServletContext().getAttribute("registration_validator");
        abstractFormMaker = (AbstractFormMaker) config.getServletContext().getAttribute("form_maker");
        userService = (UserService) config.getServletContext().getAttribute("user_service");
        mailSender = (MailSender) config.getServletContext().getAttribute("mail_sender");
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
            request.setAttribute("errors", errors);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/html/registration.jsp");
            requestDispatcher.forward(request, response);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doPost(request,response);
    }
}