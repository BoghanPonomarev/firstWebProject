package ua.nure.ponomarev.web.command.user;

import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.AccountService;
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

/**
 * @author Bogdan_Ponamarev.
 */
public class GetProfileCommand extends FrontCommand {
    private UserService userService;
    private AccountService accountService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        userService = (UserService) config.getServletContext().getAttribute("user_service");
        accountService = (AccountService) config.getServletContext().getAttribute("account_service");
    }
    @Override
    public void execute() throws ServletException, IOException {
        String userId= request.getParameter("user_id");
        if(userId==null){
            userId =String.valueOf( request.getSession().getAttribute("userId"));
        }
        if(userId !=null){
            User user= new User();
            user.setId(Integer.parseInt(userId));
            try {
               user =  userService.getFullUser(user);
               request.setAttribute("user",user);
               request.setAttribute("accounts",accountService.getAccounts(user.getId()));
            } catch (DbException e) {
                ExceptionHandler.handleException(e,request,response);
            } catch (CredentialException e) {
                request.setAttribute("error","This user was deleted or changer");
            }
        }

        forward(Mapping.getPagePath(Mapping.Page.USER_PROFILE_PAGE));
    }
}
