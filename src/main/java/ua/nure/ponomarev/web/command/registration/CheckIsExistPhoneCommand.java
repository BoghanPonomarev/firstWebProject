package ua.nure.ponomarev.web.command.registration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.web.command.FrontCommand;
import ua.nure.ponomarev.web.form.FormMaker;
import ua.nure.ponomarev.web.handler.ExceptionHandler;
import ua.nure.ponomarev.web.transformer.Transformer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bogdan_Ponamarev.
 */
public class CheckIsExistPhoneCommand extends FrontCommand {
    private static Logger logger = LogManager.getLogger(CheckIsExistPhoneCommand.class);
    private UserService userService;
    private FormMaker formMaker;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        userService = (UserService) config.getServletContext().getAttribute("user_service");
        formMaker = (FormMaker) config.getServletContext().getAttribute("form_maker");
    }

    /**
     * Strange logic with error because of credential exception
     *
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void execute() throws ServletException, IOException {
        String phoneNumber = request.getParameter("phone_number");
        try {
            if (userService.getFullUser(Transformer.transformToUser(formMaker.createUserForm(request))) != null) {
                response.sendError(500);
            }
        } catch (DbException e) {
            ExceptionHandler.handleException(e, request, response);
        } catch (CredentialException e) {
            logger.error("User entered invalid data");
        }
        response.getWriter().close();
    }
}
