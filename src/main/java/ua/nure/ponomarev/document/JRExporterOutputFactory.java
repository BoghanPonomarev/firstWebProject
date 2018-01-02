package ua.nure.ponomarev.document;


import net.sf.jasperreports.export.*;

import java.io.OutputStream;
import java.util.NoSuchElementException;

/**
 * Factory for creating {@link net.sf.jasperreports.export.OutputStreamExporterOutput} implementations.
 *
 * @author Bogdan_Ponamarev
 */
public class JRExporterOutputFactory {
    /**
     * Produces special {@link ExporterOutput} by given document type.
     *
     * @param stream - OutputStream.
     * @return -  special {@link ExporterOutput} for certain type.
     */
    public static ExporterOutput getExporterOutput(OutputStream stream, DocumentType documentType) {
        switch (documentType) {
            case XML:
                return new SimpleXmlExporterOutput(stream);
            case HTML:
                return new SimpleHtmlExporterOutput(stream);
            case PDF:
            case DOCX:
            case XLS:
            case XLSX:
            case PPTX:
                return new SimpleOutputStreamExporterOutput(stream);
            case RTF:
            default:
                throw new NoSuchElementException("There is no stream for " + documentType);
        }
    }
}
