package ua.nure.ponomarev.document;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import java.util.NoSuchElementException;

/**
 * Factory for creating {@link JRAbstractExporter} implementations.
 *
 * @author Bogdan_Ponamarev
 */
public class JRExporterFactory {

    /**
     * Produces special {@link JRAbstractExporter} by given document type.
     *
     * @return -  special {@link JRAbstractExporter} for certain type.
     */
    public static JRAbstractExporter getExporter() {
        return getExporter();
    }

    /**
     * Produces special {@link JRAbstractExporter} by given document type.
     *
     * @return -  special {@link JRAbstractExporter} for certain type.
     */
    public static JRAbstractExporter getExporter(DocumentType documentType) {
        switch (documentType) {
            case PDF:
                return new JRPdfExporter();
            case DOCX:
                return new JRDocxExporter();
            case XML:
                return new JRXmlExporter();
            case HTML:
                return new HtmlExporter();
            case RTF:
                return new JRRtfExporter();
            case XLS:
                return new JRXlsExporter();
            case XLSX:
                return new JRXlsxExporter();
            case CSV:
                return new JRCsvExporter();
            case PPTX:
                return new JRPptxExporter();
            default:
                throw new NoSuchElementException("There is no exporter for " + documentType + " type");
        }
    }
}
