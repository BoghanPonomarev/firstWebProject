package ua.nure.ponomarev.web.command.admin;

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
public class AdminGetRequestedAccounts extends FrontCommand {
    private AccountService accountService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        accountService = (AccountService) config.getServletContext().getAttribute("account_service");
    }
    @Override
    public void execute() throws ServletException, IOException {
        String sortStrategy =request.getParameter("sort");
        String page =request.getParameter("page");
        if(sortStrategy==null||(!sortStrategy.equals("ID")
                &&!sortStrategy.equals("NAME")
                &&!sortStrategy.equals("BALANCE"))){
            sortStrategy = "ID";
        }
        try {
            request.setAttribute("accounts",accountService.getRequestedAccounts(Integer.parseInt(page)
                    , AccountService.SortStrategy.valueOf(sortStrategy)));
            paginationFilling(Integer.parseInt(page),AccountService.SortStrategy.valueOf(sortStrategy));
        } catch (DbException e) {
            ExceptionHandler.handleException(e,request,response);
        }
    }
    private void paginationFilling(int numberOfPage, AccountService.SortStrategy strategy) throws DbException {
        if (!accountService.getRequestedAccounts(numberOfPage+1
                , strategy).isEmpty()) {
            request.setAttribute("nextPage", servletContext.getContextPath()
                    + "/admin/accounts/requested?page=" + (numberOfPage + 1) +"&sort="+strategy.name());
            if (!accountService.getRequestedAccounts(numberOfPage+2
                    , strategy).isEmpty()) {
                request.setAttribute("doubleNextPage", servletContext.getContextPath()
                        + "/admin/accounts/requested?page=" + (numberOfPage + 2)+"&sort="+strategy.name());
            }
        }
        if (numberOfPage > 1) {
            request.setAttribute("previousPage", servletContext.getContextPath()
                    + "/admin/accounts/requested?page=" + (numberOfPage - 1 +"&sort="+strategy.name()));
            if (numberOfPage>2&&!accountService.getRequestedAccounts(numberOfPage-2
                    , strategy).isEmpty()) {
                request.setAttribute("doublePreviousPage", servletContext.getContextPath()
                        + "/admin/accounts/requested?page=" + (numberOfPage - 2)+"&sort="+strategy.name());
            }
        }
    }
}
