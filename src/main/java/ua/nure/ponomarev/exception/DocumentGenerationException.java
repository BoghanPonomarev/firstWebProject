package ua.nure.ponomarev.exception;

/**
 * Indicates error that occurred while document processing.
 *
 * @author Bogdan_Ponamarev.
 */
public class DocumentGenerationException extends RuntimeException {

    public DocumentGenerationException(String message) {
        super(message);
    }

}
