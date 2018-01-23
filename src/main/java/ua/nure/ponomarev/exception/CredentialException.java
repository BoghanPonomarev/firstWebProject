package ua.nure.ponomarev.exception;

import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public class CredentialException extends Exception {
    private List<String> errors;

    public CredentialException(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
