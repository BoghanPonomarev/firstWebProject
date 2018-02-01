package ua.nure.ponomarev.web.command.account.payment;

import ua.nure.ponomarev.service.PaymentService;
import ua.nure.ponomarev.web.command.FrontCommand;
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
public class GetSuccessfulPaymentFormCommand extends FrontCommand {
    private PaymentService paymentService;
    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        paymentService = (PaymentService) config.getServletContext().getAttribute("payment_service");

    }
    @Override
    public void execute() throws ServletException, IOException {
        String currency = request.getParameter("currency");
        String amount = request.getParameter("amount");
        String recipient = request.getParameter("recipient");
        String paymentId = request.getParameter("id");
        if(currency==null||amount==null||recipient==null||paymentId==null){
            response.sendError(404);
        }
        request.setAttribute("id",paymentId);
        request.setAttribute("currency",currency);
        request.setAttribute("amount",amount);
        request.setAttribute("recipient",recipient);
        forward(Mapping.getPagePath(Mapping.Page.PAYMENT_SUCCESSFUL_PAGE));
    }
}
