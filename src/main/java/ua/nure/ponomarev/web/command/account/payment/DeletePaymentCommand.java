package ua.nure.ponomarev.web.command.account.payment;

import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.PaymentService;
import ua.nure.ponomarev.service.UserService;
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
public class DeletePaymentCommand extends FrontCommand {
    private PaymentService paymentService;
    private UserService userService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        paymentService = (PaymentService) config.getServletContext().getAttribute("payment_service");
        userService = (UserService) config.getServletContext().getAttribute("user_service");
    }

    @Override
    public void execute() throws ServletException, IOException {
        String id= request.getParameter("payment_id");
        if(id!=null&&id.chars().allMatch(Character::isDigit)) {
            try {

                paymentService.deletePayment(Integer.parseInt(id),(Integer)request.getSession().getAttribute("userId"));
                redirect(servletContext.getContextPath() + Mapping.getPagePath(Mapping.Page.PAYMENT_DELETING_PAGE));
            } catch (DbException e) {
                ExceptionHandler.handleException(e,request,response);
            } catch (CredentialException e) {
                request.setAttribute("errors",e.getErrors());
               forward(Mapping.getPagePath(Mapping.Page.PAYMENT_DELETING_PAGE));
            }
        }
        else{
            response.sendError(500);
        }
    }
}
