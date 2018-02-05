package ua.nure.ponomarev.document;

import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public interface ReportGenerator {
    byte[] generateReport(DocumentType documentType, String reportName, List<? extends Object> data);
}
