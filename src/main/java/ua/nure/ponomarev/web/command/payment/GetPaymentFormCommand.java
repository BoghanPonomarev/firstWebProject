package ua.nure.ponomarev.web.command.payment;

import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.AccountService;
import ua.nure.ponomarev.service.CurrencyService;
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
public class GetPaymentFormCommand extends FrontCommand {
    private AccountService accountService;
    private CurrencyService currencyService;
    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        accountService = (AccountService) config.getServletContext().getAttribute("account_service");
        currencyService = (CurrencyService) config.getServletContext().getAttribute("currency_service");
    }
    @Override
    public void execute() throws ServletException, IOException {
        int id = (Integer) request.getSession().getAttribute("userId");
        try {
            request.setAttribute("accounts", accountService.getAccounts(id));
            request.setAttribute("currency",currencyService.getCurrency());
        } catch (DbException e) {
            ExceptionHandler.handleException(e,request,response);
        }
        forward(Mapping.getPagePath(Mapping.Page.PAYMENT_ADD_PAGE));
    }
}
