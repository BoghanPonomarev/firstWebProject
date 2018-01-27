package ua.nure.ponomarev.web.command.payment;

import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.AccountService;
import ua.nure.ponomarev.service.CurrencyService;
import ua.nure.ponomarev.service.PaymentService;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.web.command.FrontCommand;
import ua.nure.ponomarev.web.form.FormMaker;
import ua.nure.ponomarev.web.form.impl.PaymentForm;
import ua.nure.ponomarev.web.handler.ExceptionHandler;
import ua.nure.ponomarev.web.page.Mapping;
import ua.nure.ponomarev.web.validator.PaymentValidator;

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
public class CheckPaymentFormCommand extends FrontCommand {

    private AccountService accountService;
    private CurrencyService currencyService;
    private FormMaker formMaker;
    private PaymentValidator paymentValidator;
    private PaymentService paymentService;
    private UserService userService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        accountService = (AccountService) config.getServletContext().getAttribute("account_service");
        currencyService = (CurrencyService) config.getServletContext().getAttribute("currency_service");
        formMaker = (FormMaker) config.getServletContext().getAttribute("form_maker");
        paymentValidator = (PaymentValidator) config.getServletContext().getAttribute("payment_validator");
        paymentService = (PaymentService) config.getServletContext().getAttribute("payment_service");
        userService = (UserService) config.getServletContext().getAttribute("user_service");
    }

    @Override
    public void execute() throws ServletException, IOException {
        PaymentForm form = formMaker.createPaymentForm(request);
        List<String> errors = paymentValidator.validate(form);
        try {
            int id = (Integer) request.getSession().getAttribute("userId");
            request.setAttribute("accounts", accountService.getAccounts(id));
            request.setAttribute("currency", currencyService.getCurrency());
            if (!errors.isEmpty()) {
                request.setAttribute("errors", errors);
                forward(Mapping.getPagePath(Mapping.Page.PAYMENT_ADD_PAGE));
            } else {
                User user = new User();
                user.setId((Integer) request.getSession().getAttribute("userId"));
                user.setPassword(form.getPassword());
                userService.getFullUser(user);
                int paymentId = paymentService.preparePayment(form.getAccountId(), form.getRecipientAccountIdentity()
                        , BigDecimal.valueOf(Double.parseDouble(form.getAmount())), form.getCurrency()
                ,(Integer)request.getSession().getAttribute("userId"));
                redirect(request.getContextPath() + "/payments/successful_prepared?currency=" + form.getCurrency() + "&amount=" + form.getAmount()
                        + "&recipient=" + form.getRecipientAccountIdentity() + "&id=" + paymentId);
            }
        } catch (CredentialException e1) {
            errors.addAll(e1.getErrors());
            if (errors.contains("There is no such user")) {
                errors.add("Wrong password");
                errors.remove("There is no such user");
            }
            request.setAttribute("identity", form.getRecipientAccountIdentity());
            request.setAttribute("amount", form.getAmount());
            request.setAttribute("errors", errors);
            forward(Mapping.getPagePath(Mapping.Page.PAYMENT_ADD_PAGE));
        } catch (DbException e1) {
            ExceptionHandler.handleException(e1, request, response);
        }
    }
}
