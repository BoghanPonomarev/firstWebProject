package ua.nure.ponomarev.document;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.export.SimpleExporterInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.exeption.DocumentGenerationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

/**
 * Report generator for generating documents of different types.
 *
 * @author Bogdan_Ponamarev.
 */
public class ReportGenerator {

    private static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final String TEMP_COMPILED_DIRECTORY_PREFIX = "compiled";
    private String compiledReportPath;
    private String reportPath;

    public ReportGenerator() throws IOException {
        compiledReportPath = getTempDirectory();
        reportPath = "reports";
    }

    /**
     * Gets temp directory of OS.
     *
     * @return path to temp directory.
     * @throws IOException - if some exception with IO occurred.
     */
    private String getTempDirectory() throws IOException {
        Path tempDirectory = Files.createTempDirectory(TEMP_COMPILED_DIRECTORY_PREFIX);
        tempDirectory.toFile().deleteOnExit();
        return tempDirectory.toString();
    }

    /**
     * Generate report by given type and report name.
     *
     * @param documentType - type of document.
     * @param reportName   - name of the report without extension.
     * @param data         - data for report.
     * @return generated report in bytes.
     */
    byte[] generateReport(DocumentType documentType, String reportName, List<? extends Object> data) {
        try {
            JasperReport jasperReport = ReportUtil.loadReport(reportName, reportPath, compiledReportPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(),
                    new JRBeanCollectionDataSource(data, false));
            return exportReport(jasperPrint, documentType);
        } catch (IOException | JRException e) {
            log.debug("Document generation failed. Document name: {}", e, reportName);
            throw new DocumentGenerationException("Cannot generate document " + reportName);
        }
    }

    /**
     * Exports report to given format.
     *
     * @param jasperPrint  - assembled document in jasper report.
     * @param documentType - format of document.
     * @return - report in bytes.
     * @throws JRException - if some exception with exporting occurred.
     */
    private byte[] exportReport(JasperPrint jasperPrint, DocumentType documentType) throws JRException {
        ByteArrayOutputStream tempStream = new ByteArrayOutputStream();
        JRAbstractExporter exporter = JRExporterFactory.getExporter(documentType);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(JRExporterOutputFactory.getExporterOutput(tempStream, documentType));
        exporter.exportReport();
        return tempStream.toByteArray();
    }
}
