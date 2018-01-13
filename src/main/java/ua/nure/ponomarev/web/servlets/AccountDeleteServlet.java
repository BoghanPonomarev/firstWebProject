package ua.nure.ponomarev.web.servlets;

import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.service.AccountService;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.web.form.FormMaker;
import ua.nure.ponomarev.web.handler.ExceptionHandler;
import ua.nure.ponomarev.web.validator.AccountValidator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
@WebServlet(name = "AccountDeleteServlet" ,urlPatterns = "/account_delete")
public class AccountDeleteServlet extends HttpServlet {
    private AccountService accountService;
    private FormMaker formMaker;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        accountService = (AccountService) config.getServletContext().getAttribute("account_service");
        formMaker = (FormMaker) config.getServletContext().getAttribute("form_maker");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idToDelete = request.getParameter("id");
        if(idToDelete!=null&&idToDelete.chars().allMatch(Character::isDigit)) {
            try {
                accountService.delete(Integer.parseInt(idToDelete));
            } catch (DBException e) {
                ExceptionHandler.handleException(e,request,response);
            }
        }
        int id= (int) request.getSession().getAttribute("authorization");
        List<Account> accounts= new ArrayList<>();
        try {
            accounts.addAll(accountService.getAccounts(id));
        } catch (DBException e) {
            ExceptionHandler.handleException(e,request,response);
        }
        request.setAttribute("accounts", accounts);
        response.sendRedirect("accounts");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doPost(request,response);
    }
}
