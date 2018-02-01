package ua.nure.ponomarev.web.command.account.payment;

import ua.nure.ponomarev.entity.Payment;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.AccountService;
import ua.nure.ponomarev.service.PaymentService;
import ua.nure.ponomarev.web.command.FrontCommand;
import ua.nure.ponomarev.web.form.impl.PaymentShowForm;
import ua.nure.ponomarev.web.handler.ExceptionHandler;
import ua.nure.ponomarev.web.page.Mapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public class ShowPaymentsCommand extends FrontCommand {
    private AccountService accountService;
    private PaymentService paymentService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        accountService = (AccountService) config.getServletContext().getAttribute("account_service");
        paymentService = (PaymentService) config.getServletContext().getAttribute("payment_service");
    }

    @Override
    public void execute() throws ServletException, IOException {
        putUsersToRequest(request, response);
        forward(Mapping.getPagePath(Mapping.Page.PAYMENT_SHOW_ALL));
    }

    private void putUsersToRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<PaymentShowForm> payments=new ArrayList<>();
        String page;
        int numberOfPage = 1;
        if ((page = request.getParameter("page")) != null && page.chars().allMatch(Character::isDigit)) {
            numberOfPage = Integer.parseInt((String) request.getParameter("page"));
        }
        try {
            String sortStrategy = request.getParameter("sort");
            if(sortStrategy==null||(!sortStrategy.equals("FIRST_NEW")
            &&!sortStrategy.equals("FIRST_OLD")
            &&!sortStrategy.equals("FIRST_ID"))){
                sortStrategy = "ID";
            }
           for(Payment payment:paymentService.getPayments((Integer)request.getSession().getAttribute("userId")
                   ,numberOfPage, PaymentService.Strategy.valueOf(sortStrategy))){
           payments.add(convertToForm(payment));
            }
            paginationValidation(numberOfPage, PaymentService.Strategy.valueOf(sortStrategy));
        } catch (DbException e) {
            ExceptionHandler.handleException(e, request, response);
        }
        request.setAttribute("payments",payments);
        request.setAttribute("page", numberOfPage);
    }

    private void paginationValidation(int numberOfPage,PaymentService.Strategy strategy) throws DbException {
        if (!paymentService.getPayments((Integer) request.getSession().getAttribute("userId")
                , numberOfPage + 1,strategy).isEmpty()) {
            request.setAttribute("nextPage", servletContext.getContextPath()
                    + "/payments?page=" + (numberOfPage + 1) +"&sort="+strategy.name());
            if (!paymentService.getPayments((Integer) request.getSession().getAttribute("userId")
                    , numberOfPage + 2,strategy).isEmpty()) {
                request.setAttribute("doubleNextPage", servletContext.getContextPath()
                        + "/payments?page=" + (numberOfPage + 2)+"&sort="+strategy.name());
            }
        }
        if (numberOfPage > 1) {
            request.setAttribute("previousPage", servletContext.getContextPath()
                    + "/payments?page=" + (numberOfPage - 1 +"&sort="+strategy.name()));
            if (numberOfPage>2&&!paymentService.getPayments((Integer) request.getSession().getAttribute("userId")
                    , numberOfPage - 2,strategy).isEmpty()) {
                request.setAttribute("doublePreviousPage", servletContext.getContextPath()
                        + "/payments?page=" + (numberOfPage - 2)+"&sort="+strategy.name());
            }
        }
    }

    private PaymentShowForm convertToForm(Payment payment) throws DbException {
        PaymentShowForm form = new PaymentShowForm();
        form.setAmount(payment.getAmount());
        form.setCurrency(payment.getCurrency());
        form.setDate(payment.getDate());
        form.setId(payment.getId());
        form.setStatus(payment.getStatus());
        form.setType(payment.getType());
        if(payment.getType()==Payment.Type.OUTGOING) {
            form.setRecipient(accountService.get(payment.getRecipientId()).getName());
            form.setSender(accountService.get(payment.getSenderId()).getName());
        }
        else{
            form.setSender(accountService.get(payment.getRecipientId()).getName());
            form.setRecipient(accountService.get(payment.getSenderId()).getName());
        }
        return form;
    }
}
