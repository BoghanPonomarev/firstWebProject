package ua.nure.ponomarev.web.command.account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.exception.TransformationException;
import ua.nure.ponomarev.service.AccountService;
import ua.nure.ponomarev.web.command.FrontCommand;
import ua.nure.ponomarev.web.form.AccountForm;
import ua.nure.ponomarev.web.form.FormMaker;
import ua.nure.ponomarev.web.handler.ExceptionHandler;
import ua.nure.ponomarev.web.page.Mapping;
import ua.nure.ponomarev.web.transformer.Transformer;
import ua.nure.ponomarev.web.validator.AccountValidator;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public class AddAccountsCommand extends FrontCommand {
    private static Logger logger = LogManager.getLogger(AddAccountsCommand.class);
    private AccountService accountService;
    private AccountValidator accountValidator;
    private FormMaker formMaker;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response,  servletContext, config);
        accountService= (AccountService) config.getServletContext().getAttribute("account_service");
        accountValidator = (AccountValidator) config.getServletContext().getAttribute("account_validator");
        formMaker = (FormMaker) config.getServletContext().getAttribute("form_maker");
    }

    @Override
    public void execute() throws ServletException, IOException {
        AccountForm accountForm = formMaker.createAccountForm(request);
        List<String> errors = accountValidator.validate(accountForm);
        try {
            request.setAttribute("errors", errors);
            Account account = Transformer.transformToAccount(accountForm);
            if(accountService.isExistAccount(account.getId(),account.getCard().getCardNumber())){
            errors.add("Card with this card number is already exist");
            }
            if(errors.isEmpty()) {
                    accountService.putAccount(account, (int) request.getSession().getAttribute("userId"));
                    redirect("/show_accounts");
            }
        } catch (DBException  e) {
            ExceptionHandler.handleException(e,request,response);
        } catch (TransformationException e) {
            logger.error("There is something wrong in account transformation");
        }
        if(!errors.isEmpty()) {
            forward(Mapping.getPagePath(Mapping.Page.ACCOUNTS_PAGE));
        }
    }

}
