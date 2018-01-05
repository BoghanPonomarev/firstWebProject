package ua.nure.ponomarev.web.servlets;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.encoder.Encoder;
import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.web.form.AuthorizationForm;
import ua.nure.ponomarev.web.form.FormMaker;
import ua.nure.ponomarev.web.handler.ExceptionHandler;
import ua.nure.ponomarev.web.validator.AuthorizationValidator;

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
@WebServlet(name = "AuthorizationServlet", urlPatterns = "/authorization")
public class AuthorizationServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(AuthorizationServlet.class);
    private UserService userService;
    private AuthorizationValidator authorizationValidator;
    private FormMaker formMaker;
    private Encoder encoder;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) config.getServletContext().getAttribute("user_service");
        authorizationValidator = (AuthorizationValidator) config.getServletContext().getAttribute("authorization_validator");
        formMaker = (FormMaker) config.getServletContext().getAttribute("form_maker");
        encoder = (Encoder) config.getServletContext().getAttribute("encoder");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      AuthorizationForm authorizationForm= formMaker.createAuthorizationForm(request);
      Map<String,String> errors = authorizationValidator.validate(authorizationForm);
      String phoneNumber =request.getParameter("phone_number");
      String password =request.getParameter("password");
        try {
            if (!userService.isCanEntry(phoneNumber,password)) {
                errors.put("parametrDontExist", "Wrong phone number or password");
            }
        } catch (DBException e) {
            ExceptionHandler.handleException(e,request,response);
        }
      if(!errors.isEmpty()){
          logger.info("User made some authorization mistakes");
          request.setAttribute("errors", errors);
          RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/jsp/authorization.jsp");
          requestDispatcher.forward(request, response);
      }
      request.getSession().setAttribute("authorization",encoder.encrypt(phoneNumber+" "+password));
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/html/index.jsp");
        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
