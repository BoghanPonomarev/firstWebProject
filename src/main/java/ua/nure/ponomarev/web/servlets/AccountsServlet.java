package ua.nure.ponomarev.web.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.encoder.Encoder;
import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.exception.TransformationException;
import ua.nure.ponomarev.service.AccountService;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.web.form.AccountForm;
import ua.nure.ponomarev.web.form.FormMaker;
import ua.nure.ponomarev.web.handler.ExceptionHandler;
import ua.nure.ponomarev.web.transformer.Transformer;
import ua.nure.ponomarev.web.validator.AccountValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @author Bogdan_Ponamarev.
 */
@WebServlet(name = "AccountsServlet",urlPatterns = "/accounts")
public class AccountsServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(AuthorizationServlet.class);
    private UserService userService;
    private AccountService accountService;
    private AccountValidator accountValidator;
    private FormMaker formMaker;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) config.getServletContext().getAttribute("user_service");
        accountService= (AccountService) config.getServletContext().getAttribute("account_service");
        accountValidator = (AccountValidator) config.getServletContext().getAttribute("account_validator");
        formMaker = (FormMaker) config.getServletContext().getAttribute("form_maker");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountForm accountForm = formMaker.createAccountForm(request);
    Map<String,String> errors = accountValidator.validate(accountForm);
    putAccountsToRequestById(request,response);
        try {
            Account account =Transformer.transformToAccount(accountForm);
            if(accountService.isValidAccount(account.getId(),account.getCard().getCardNumber())){
                accountService.putAccount(account,(int) request.getSession().getAttribute("authorization"));
            }
        } catch (DBException e) {
            ExceptionHandler.handleException(e,request,response);
        } catch (TransformationException e) {
        logger.error("There is something wrong in account transformation");
        }
        if(!errors.isEmpty()){
            request.setAttribute("errors", errors);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/jsp/accounts.jsp");
            requestDispatcher.forward(request, response);
        }else {
            response.sendRedirect("accounts");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        putAccountsToRequestById(request,response);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/jsp/accounts.jsp");
        requestDispatcher.forward(request, response);
    }
    private void putAccountsToRequestById(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        int id= (int) request.getSession().getAttribute("authorization");
        List<Account> accounts= new ArrayList<>();
        try {
            accounts.addAll(accountService.getAccounts(id));
        } catch (DBException e) {
            ExceptionHandler.handleException(e,request,response);
        }
        request.setAttribute("accounts", accounts);
    }
}
