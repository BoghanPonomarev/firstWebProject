package ua.nure.ponomarev.exception;

/**
 * @author Bogdan_Ponamarev.
 */
public abstract class LogicException extends Throwable {
    public LogicException(String massage, Exception ex) {
        super(massage, ex);
    }

    public LogicException(String massage) {
        super(massage);
    }

    public abstract ExceptionType getType();

    public enum ExceptionType {
        SERVER_EXCEPTION,
        USER_EXCEPTION
    }
}
