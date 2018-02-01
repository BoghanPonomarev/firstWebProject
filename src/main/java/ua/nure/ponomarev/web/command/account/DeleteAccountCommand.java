package ua.nure.ponomarev.web.command.account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.AccountService;
import ua.nure.ponomarev.web.command.FrontCommand;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Bogdan_Ponamarev.
 */
public class DeleteAccountCommand extends FrontCommand {
    private static Logger logger = LogManager.getLogger(DeleteAccountCommand.class);
    private AccountService accountService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        accountService = (AccountService) config.getServletContext().getAttribute("account_service");
    }

    @Override
    public void execute() throws ServletException, IOException {
        String password = request.getParameter("password");
        if (password != null) {
            PrintWriter out = response.getWriter();
            try {
                accountService.delete(Integer.parseInt(request.getParameter("accountId")), password);
            } catch (CredentialException e) {
                logger.error("User entered invalid data");
                response.sendError(500);
            } catch (DbException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }
}
