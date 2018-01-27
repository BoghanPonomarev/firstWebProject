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
public class AdminBanAccountCommand extends FrontCommand {
    private AccountService accountService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        accountService = (AccountService) config.getServletContext().getAttribute("account_service");
    }

    @Override
    public void execute() throws ServletException, IOException {
        String id = request.getParameter("account_id");
        String user_id = request.getParameter("user_id");
        try {
            accountService.setBanValue(Integer.parseInt(id), !Boolean.parseBoolean(request.getParameter("ban_status")));
        } catch (DbException e) {
            ExceptionHandler.handleException(e, request, response);
        }
        redirect(servletContext.getContextPath()+ "/user/profile?user_id="+user_id);
    }
}
