package ua.nure.ponomarev.web.command.admin;

import ua.nure.ponomarev.exception.CredentialException;
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
public class AdminSkipRequestCommand extends FrontCommand {
    private AccountService accountService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        accountService = (AccountService) config.getServletContext().getAttribute("account_service");
    }
    @Override
    public void execute() throws ServletException, IOException {
        try {
            accountService.setRequestedValue(Integer.parseInt(request.getParameter("accountId")),true);
            redirect(request.getContextPath()+request.getParameter("link"));
        } catch (DbException e) {
            ExceptionHandler.handleException(e,request,response);
        } catch (CredentialException e) {
            redirect(request.getContextPath()+"/admin/accounts/requested");
        }
    }
}
