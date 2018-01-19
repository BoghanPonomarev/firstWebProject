package ua.nure.ponomarev.web.command.account;

import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.hash.Hash;
import ua.nure.ponomarev.service.AccountService;
import ua.nure.ponomarev.service.UserService;
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
    private UserService userService;
    private Hash hash;
    private AccountService accountService;
    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        userService = (UserService) config.getServletContext().getAttribute("user_service");
        hash = (Hash) config.getServletContext().getAttribute("hash");
        accountService = (AccountService) config.getServletContext().getAttribute("account_service");
    }

    @Override
    public void execute() throws ServletException, IOException {
        String password = request.getParameter("password");
        if(password!=null){
            PrintWriter out = response.getWriter();
            try {
                User user =userService.getUser((Integer)request.getSession().getAttribute("userId"));
                if(user.getPassword().equals(hash.getHash(password))) {
                    String idToDelete = request.getParameter("account_id");
                    accountService.delete(Integer.parseInt(idToDelete));
                }else {
                    response.sendError(500);
                }
            } catch (DBException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }
}
