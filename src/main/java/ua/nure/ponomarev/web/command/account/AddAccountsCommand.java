package ua.nure.ponomarev.web.command.account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.AccountService;
import ua.nure.ponomarev.web.command.FrontCommand;
import ua.nure.ponomarev.web.form.impl.AccountForm;
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
        super.init(request, response, servletContext, config);
        accountService = (AccountService) config.getServletContext().getAttribute("account_service");
        accountValidator = (AccountValidator) config.getServletContext().getAttribute("account_validator");
        formMaker = (FormMaker) config.getServletContext().getAttribute("form_maker");
    }

    @Override
    public void execute() throws ServletException, IOException {
        AccountForm accountForm = formMaker.createAccountForm(request);
        List<String> errors = accountValidator.validate(accountForm);
        try {
            Account account = Transformer.transformToAccount(accountForm);
            if (errors.isEmpty()) {
                try {
                    accountService.putAccount(account, (int) request.getSession().getAttribute("userId"));
                    redirect(request.getContextPath() + "/user/profile");
                } catch (CredentialException e) {
                    logger.error("User entered invalid data");
                    errors.addAll(e.getErrors());
                }
            }
        } catch (DbException e) {
            ExceptionHandler.handleException(e, request, response);
        }
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            forward(Mapping.getPagePath(Mapping.Page.USER_ADD_ACCOUNT_PAGE));
        }
    }

}
