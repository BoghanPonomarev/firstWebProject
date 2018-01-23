package ua.nure.ponomarev.web.command.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.web.command.FrontCommand;
import ua.nure.ponomarev.web.handler.ExceptionHandler;
import ua.nure.ponomarev.web.page.Mapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bogdan_Ponamarev.
 */
public class GetUserPageCommand extends FrontCommand {
    private static Logger logger = LogManager.getLogger(GetUserPageCommand.class);
    private UserService userService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        userService = (UserService) config.getServletContext().getAttribute("user_service");
    }

    @Override
    public void execute() throws ServletException, IOException {
        Object idObj = request.getSession().getAttribute("userId");
        if (idObj != null) {
            int id = (int) idObj;
            try {
                User user = new User();
                user.setId(id);
                user = userService.getFullUser(user);
                Map<String, String> parameters = new HashMap<>();
                parameters.put("phoneNumber", user.getPhoneNumber());
                if (user.getEmail() != null) {
                    parameters.put("email", user.getEmail());
                }
                if (user.getFirstName() != null) {
                    parameters.put("firstName", user.getFirstName());
                }
                if (user.getSecondName() != null) {
                    parameters.put("secondName", user.getSecondName());
                }
                if (user.getThirdName() != null) {
                    parameters.put("thirdName", user.getThirdName());
                }
                request.setAttribute("parameters", parameters);
            } catch (DbException e) {
                ExceptionHandler.handleException(e, request, response);
            } catch (CredentialException e) {
                logger.error("There is exception refer with credential");
            }
            forward(Mapping.getPagePath(Mapping.Page.USER_PROFILE_PAGE));
        }
    }
}
