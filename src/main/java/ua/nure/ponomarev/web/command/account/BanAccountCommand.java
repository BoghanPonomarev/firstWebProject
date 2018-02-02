package ua.nure.ponomarev.web.command.account;

import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.AccountService;
import ua.nure.ponomarev.web.command.FrontCommand;
import ua.nure.ponomarev.web.handler.ExceptionHandler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bogdan_Ponamarev.
 */
public class BanAccountCommand extends FrontCommand {
    private AccountService accountService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        accountService = (AccountService) config.getServletContext().getAttribute("account_service");
    }

    @Override
    public void execute() throws ServletException, IOException {
        String id = request.getParameter("accountId");
        String userId = request.getParameter("userId");
        try {
            accountService.setBanValue(Integer.parseInt(id));
        } catch (DbException e) {
            ExceptionHandler.handleException(e, request, response);
        }
        redirect(servletContext.getContextPath()+ "/user/profile?userId="+userId);
    }
}
