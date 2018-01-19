package ua.nure.ponomarev.web.command.authorization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.web.command.FrontCommand;
import ua.nure.ponomarev.web.form.AuthorizationForm;
import ua.nure.ponomarev.web.form.FormMaker;
import ua.nure.ponomarev.web.handler.ExceptionHandler;
import ua.nure.ponomarev.web.page.Mapping;
import ua.nure.ponomarev.web.validator.AuthorizationValidator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public class AuthorizationCheckCommand extends FrontCommand {
    private static Logger logger = LogManager.getLogger(AuthorizationCheckCommand.class);
    private UserService userService;
    private AuthorizationValidator authorizationValidator;
    private FormMaker formMaker;


    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response,  servletContext, config);
        userService = (UserService) config.getServletContext().getAttribute("user_service");
        authorizationValidator = (AuthorizationValidator) config.getServletContext().getAttribute("authorization_validator");
        formMaker = (FormMaker) config.getServletContext().getAttribute("form_maker");
    }

    @Override
    public void execute() throws ServletException, IOException {
        AuthorizationForm authorizationForm= formMaker.createAuthorizationForm(request);
        List<String> errors = authorizationValidator.validate(authorizationForm);
        String phoneNumber =request.getParameter("phone_number");
        String password =request.getParameter("password");
        User user;
        try {
            user=userService.getUser(phoneNumber,password);
            if (user==null) {
                errors.add("Wrong phone number or password");
            }else{
                request.getSession().setAttribute("userId",user.getId());
                request.getSession().setAttribute("userRole",user.getRole());
                forward("/accounts/show_accounts");
            }
        } catch (DBException e) {
            ExceptionHandler.handleException(e,request,response);
        }
        if(!errors.isEmpty()){
            logger.info("User made some authorization mistakes");
            request.setAttribute("errors", errors);
            forward(Mapping.getPagePath(Mapping.Page.AUTHORIZATION_PAGE));
        }

    }
}
