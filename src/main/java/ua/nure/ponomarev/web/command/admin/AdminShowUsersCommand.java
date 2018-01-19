package ua.nure.ponomarev.web.command.admin;

import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.DBException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bogdan_Ponamarev.
 */
public class AdminShowUsersCommand extends FrontCommand {
    private UserService userService;
    private AccountService accountService;
    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response,  servletContext, config);
        userService = (UserService) config.getServletContext().getAttribute("user_service");
        accountService = (AccountService) config.getServletContext().getAttribute("account_service");
    }
    @Override
    public void execute() throws ServletException, IOException {
        putUsersToRequest(request,response);
        forward(Mapping.getPagePath(Mapping.Page.ADMIN_PAGE));
    }
    private void putUsersToRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<User,List<Account>> users = new HashMap<>();
        try {
            for (User user:userService.getAll()) {
                users.put(user,accountService.getAccounts(user.getId()));
            }
        } catch (DBException e) {
            ExceptionHandler.handleException(e, request, response);
        }
        request.setAttribute("users", users);
    }
}
