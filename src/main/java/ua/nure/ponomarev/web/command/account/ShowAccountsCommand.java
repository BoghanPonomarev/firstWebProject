package ua.nure.ponomarev.web.command.account;
import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.exception.DBException;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public class ShowAccountsCommand extends FrontCommand {
    private AccountService accountService;
    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response,  servletContext, config);
        accountService = (AccountService) config.getServletContext().getAttribute("account_service");
    }
    @Override
    public void execute() throws ServletException, IOException {
        putAccountsAndRedirect(request,response);
        forward(Mapping.getPagePath(Mapping.Page.ACCOUNTS_PAGE));
    }
    private void putAccountsAndRedirect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id= (int) request.getSession().getAttribute("userId");
        List<Account> accounts= new ArrayList<>();
        try {
            accounts.addAll(accountService.getAccounts(id));
        } catch (DBException e) {
            ExceptionHandler.handleException(e,request,response);
        }
        request.setAttribute("accounts", accounts);
    }
}
