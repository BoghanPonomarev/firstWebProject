package ua.nure.ponomarev.web.command.account.replenish;

import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.AccountService;
import ua.nure.ponomarev.service.CurrencyService;
import ua.nure.ponomarev.service.PaymentService;
import ua.nure.ponomarev.web.command.FrontCommand;
import ua.nure.ponomarev.web.form.Form;
import ua.nure.ponomarev.web.form.FormMaker;
import ua.nure.ponomarev.web.form.impl.ReplenishForm;
import ua.nure.ponomarev.web.handler.ExceptionHandler;
import ua.nure.ponomarev.web.page.Mapping;
import ua.nure.ponomarev.web.validator.ReplenishValidator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
/**
 * @author Bogdan_Ponamarev.
 */
public class ExecuteReplenishCommand extends FrontCommand {
    private AccountService accountService;
    private ReplenishValidator replenishValidator;
    private FormMaker formMaker;
    private CurrencyService currencyService;
    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        accountService = (AccountService) config.getServletContext().getAttribute("account_service");
        replenishValidator = (ReplenishValidator) config.getServletContext().getAttribute("replenish_validator");
        formMaker = (FormMaker) config.getServletContext().getAttribute("form_maker");
        currencyService = (CurrencyService) config.getServletContext().getAttribute("currency_service");
    }

    @Override
    public void execute() throws ServletException, IOException {
        List<String> errors = replenishValidator.validate(formMaker.createReplenishForm(request));
        request.setAttribute("errors", errors);
        if (!errors.isEmpty()) {
            try {
                request.setAttribute("accounts", accountService.getAccounts((Integer)request.getSession().getAttribute("userId")
                        , AccountService.SortStrategy.ID));
                request.setAttribute("currency",currencyService.getCurrency());
            } catch (DbException e) {
                ExceptionHandler.handleException(e,request,response);
            }
            forward(Mapping.getPagePath(Mapping.Page.REPLENISH_FORM_PAGE));
        } else {
            try {
                accountService.replenishAccount(BigDecimal.valueOf(Double.parseDouble(request.getParameter("amount")))
                        , request.getParameter("currency"), request.getParameter("account_name"));
            } catch (DbException e) {
                ExceptionHandler.handleException(e, request, response);
            } catch (CredentialException e) {
                errors.addAll(e.getErrors());
                forward(Mapping.getPagePath(Mapping.Page.REPLENISH_FORM_PAGE));
            }
            redirect(servletContext.getContextPath() + Mapping.getPagePath(Mapping.Page.REPLENISH_SUCCESS_PAGE));
        }
    }
}
