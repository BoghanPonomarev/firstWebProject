package ua.nure.ponomarev.document;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.util.NoSuchElementException;

/**
 * Represents document in mime type and extension.
 *
 * @author Bogdan_Ponamarev.
 */
public enum DocumentType {

    DOC("doc", "application/msword"),
    DOCX("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    PDF("pdf", "application/pdf"),
    RTF("rtf", "application/rtf"),
    XML("xml", "application/xml"),
    TEXT("text", "text/plain"),
    TXT("txt", "text/plain"),
    _1C("txt", "text/plain"),
    XLS("xls", "application/vnd.ms-excel"),
    XLSX("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    HTML("html", "text/html"),
    CSV("csv", "text/plain"),
    PPTX("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");

    private static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final String contentType;
    private final String extension;

    DocumentType(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    /**
     * Constricts {@link DocumentType} from given format.
     *
     * @param format - case insensitive format of document.
     * @return {@link DocumentType} - type of document.
     */
    public static DocumentType getDocumentType(String format) {
        try {
            return DocumentType.valueOf(format.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException("Invalid format");
        }
    }

    public String getContentType() {
        return contentType;
    }

    public String getExtension() {
        return extension;
    }
}