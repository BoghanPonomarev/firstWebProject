package ua.nure.ponomarev.document;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;

/**
 * @author Bogdan_Ponamarev.
 */
public class Demo {

    private static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) throws IOException {
        log.info("Start");
        ReportGenerator reportGenerator = new ReportGenerator();
        byte[] report = reportGenerator.generateReport(DocumentType.getDocumentType("PDF"),
                "payment",
                Arrays.asList(RenderPaymentDto.builder()
                        .amount("11")
                        .amountCursive("Одинадцать")
                        .build()));
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File("test.pdf"))) {
            fileOutputStream.write(report);
        }

    }
}
