package ua.nure.ponomarev.web.command.account.payment;

import ua.nure.ponomarev.document.DocumentType;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.PaymentService;
import ua.nure.ponomarev.web.command.FrontCommand;
import ua.nure.ponomarev.web.form.impl.PaymentShowForm;
import ua.nure.ponomarev.web.handler.ExceptionHandler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Bogdan_Ponamarev.
 */
public class GenerateReportCommand extends FrontCommand {
    private PaymentService paymentService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        paymentService = (PaymentService) config.getServletContext().getAttribute("payment_service");

    }
    @Override
    public void execute() throws ServletException, IOException {
        String type = request.getParameter("documentType");
        String paymentId = request.getParameter("paymentId");
        byte [] report = new byte[0];
        try {
            report = paymentService.generateRecord(Integer.parseInt(paymentId), DocumentType.getDocumentType(type));
        } catch (DbException e) {
            ExceptionHandler.handleException(e,request,response);
        }
        response.setHeader(HttpHeaders.CONTENT_TYPE, DocumentType.getDocumentType(type).name());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "Content-Disposition: attachment; filename=\"payment.pdf\"");
        OutputStream stream = response.getOutputStream();
        stream.write(report);
    }
}
