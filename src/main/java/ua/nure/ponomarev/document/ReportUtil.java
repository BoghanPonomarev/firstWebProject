package ua.nure.ponomarev.document;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.Objects.isNull;

/**
 * Utils that helps with jasper report files manipulation.
 *
 * @author Bogdan_Ponamarev.
 */
public class ReportUtil {
    private static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static String REPORT_EXTENSION = ".jrxml";
    private static String COMPILED_REPORT_EXTENSION = ".jasper";


    /**
     * Load particular report from reports folder.
     *
     * @param reportName - name of the report without extension.
     * @return loaded report.
     */
    public static JasperReport loadReport(String reportName, String reportPath, String compiledReportPath) throws IOException, JRException {
        log.debug("Start loading report {} ...", reportName);
        String jrxmlFile = reportPath + File.separator + reportName + REPORT_EXTENSION;
        String jasperFile = compiledReportPath + File.separator + reportName + COMPILED_REPORT_EXTENSION;
        return (JasperReport) JRLoader.loadObject(getReportStream(jrxmlFile, jasperFile, reportName, compiledReportPath));
    }

    /**
     * Gets suitable stream for document loading.
     * If there is jasper file then it will be returned
     * otherwise jrxml file will be compiled and then
     * jasper will be returned.
     *
     * @param jrxmlFile          - jrxml report.
     * @param jasperFile         - compiled report.
     * @param reportName         - name of the report without extension.
     * @param compiledReportPath - path where compiled reports are stored.
     * @return stream associated with jasper file.
     * @throws IOException - if some IO exception will be occurred.
     * @throws JRException - if some exception with report will ne occurred.
     */
    private static InputStream getReportStream(String jrxmlFile, String jasperFile, String reportName, String compiledReportPath) throws IOException, JRException {
        InputStream reportStream = ReportUtil.class.getClassLoader().getResourceAsStream(jrxmlFile);
        if (isNull(reportStream) && Files.exists(Paths.get(jasperFile))) {
            log.debug("Compiled report exists. Path: {} ", jasperFile);
            return Files.newInputStream(Paths.get(jasperFile));
        }
        getCompiledFile(reportName, compiledReportPath);
        JasperCompileManager.compileReportToStream(reportStream, new FileOutputStream(getCompiledFile(reportName, compiledReportPath)));
        reportStream = Files.newInputStream(Paths.get(jasperFile));
        return reportStream;
    }


    private static File getCompiledFile(String reportName, String compiledReportPath) {
        File file = new File(compiledReportPath + File.separator + reportName + COMPILED_REPORT_EXTENSION);
        file.deleteOnExit();
        return file;
    }
}
