package ua.nure.ponomarev.web.command.account;

import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.AccountService;
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
public class RequestForUnbanCommand extends FrontCommand {
    private AccountService accountService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        accountService = (AccountService) config.getServletContext().getAttribute("account_service");
    }

    @Override
    public void execute() throws ServletException, IOException {
        User.Role role = (User.Role) request.getSession().getAttribute("userRole");
        String accountId = request.getParameter("accountId");
        if (accountId == null || !accountId.chars().allMatch(Character::isDigit)) {
            response.sendError(500);
        } else {
            try {
                accountService.setRequestedValue(Integer.parseInt(accountId),false);
                if (role.equals(User.Role.USER)) {
                    redirect(request.getContextPath() + "/user/profile?userId=" + request.getSession().getAttribute("userId")
                            + "&sort="+request.getParameter("sort"));
                } else {
                    redirect(request.getContextPath() + request.getParameter("link"));
                }
            } catch (DbException e) {
                ExceptionHandler.handleException(e, request, response);
            } catch (CredentialException e) {
                request.setAttribute("requestError", e.getErrors().get(0));
                forward(Mapping.getPagePath(Mapping.Page.USER_PROFILE_PAGE));
            }
        }
    }
}
