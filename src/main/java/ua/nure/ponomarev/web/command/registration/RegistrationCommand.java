package ua.nure.ponomarev.web.command.registration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.web.command.FrontCommand;
import ua.nure.ponomarev.web.form.FormMaker;
import ua.nure.ponomarev.web.form.RegistrationForm;
import ua.nure.ponomarev.web.handler.ExceptionHandler;
import ua.nure.ponomarev.web.page.Mapping;
import ua.nure.ponomarev.web.transformer.Transformer;
import ua.nure.ponomarev.web.validator.RegistrationValidator;

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
public class RegistrationCommand extends FrontCommand {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private FormMaker abstractFormMaker;
    private RegistrationValidator formValidator;
    private UserService userService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        formValidator = (RegistrationValidator) config.getServletContext().getAttribute("registration_validator");
        abstractFormMaker = (FormMaker) config.getServletContext().getAttribute("form_maker");
        userService = (UserService) config.getServletContext().getAttribute("user_service");
    }

    @Override
    public void execute() throws ServletException, IOException {
        RegistrationForm registrationForm = abstractFormMaker.createRegistrationForm(request);
        List<String> errors = formValidator.validate(registrationForm);
        User user = Transformer.transformToUser(registrationForm);
        try {
            userService.add(user);
        } catch (DbException e) {
            ExceptionHandler.handleException(e, request, response);
        } catch (CredentialException e) {
            errors.addAll(e.getErrors());
        }
        if (!errors.isEmpty()) {
            logger.info("User made some registration mistakes");
            request.setAttribute("errors", errors);
            forward(Mapping.getPagePath(Mapping.Page.REGISTRATION_PAGE));
        } else {
            logger.info("User entered correct data");
            redirect("/authorization");
        }
    }
}
