package ua.nure.ponomarev.document;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * @author Bogdan_Ponamarev.
 */
@WebServlet(name = "ReportServlet", urlPatterns = "/report/generate")
public class ReportServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DocumentType documentType = DocumentType.getDocumentType(request.getParameter("type"));
        ReportGenerator reportGenerator = new ReportGenerator();
        byte[] report = reportGenerator.generateReport(DocumentType.getDocumentType("PDF"),
                "payment",
                Arrays.asList(RenderPaymentDto.builder()
                        .amount("11")
                        .amountCursive("Одинадцать")
                        .build()));
        response.setHeader(HttpHeaders.CONTENT_TYPE, documentType.getContentType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "Content-Disposition: attachment; filename=\"payment.pdf\"");
        OutputStream stream = response.getOutputStream();
        stream.write(report);
    }
}
