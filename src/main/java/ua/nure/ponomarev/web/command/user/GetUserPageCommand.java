package ua.nure.ponomarev.web.command.user;

import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.web.command.FrontCommand;
import ua.nure.ponomarev.web.handler.ExceptionHandler;
import ua.nure.ponomarev.web.page.Mapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

/**
 * @author Bogdan_Ponamarev.
 */
public class GetUserPageCommand extends FrontCommand{
    private UserService userService;
    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        userService = (UserService) config.getServletContext().getAttribute("user_service");
    }
    @Override
    public void execute() throws ServletException, IOException {
        Object idObj = request.getSession().getAttribute("authorizationId");
        if(idObj!=null){
            int id = (int)idObj;
            try {
                User user = userService.getUser(id);
                Map<String,String> parameters = new HashMap<>();
                parameters.put("phoneNumber",user.getPhoneNumber());
                if(user.getEmail()!=null) {
                    parameters.put("email", user.getEmail());
                }
                if(user.getFirstName()!=null) {
                    parameters.put("firstName", user.getFirstName());
                }
                if(user.getSecondName()!=null) {
                    parameters.put("secondName", user.getSecondName());
                }
                if(user.getThirdName()!=null) {
                    parameters.put("thirdName", user.getThirdName());
                }
                request.setAttribute("parameters", parameters);
            } catch (DBException e) {
                ExceptionHandler.handleException(e,request,response);
            }
            forward(Mapping.getPagePath(Mapping.Page.PROFILE_PAGE));
        }
    }
}
