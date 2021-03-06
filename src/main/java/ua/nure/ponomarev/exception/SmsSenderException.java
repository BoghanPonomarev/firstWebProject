package ua.nure.ponomarev.exception;

/**
 * @author Bogdan_Ponamarev.
 */
public class SmsSenderException extends LogicException {
    private LogicException.ExceptionType type;

    public SmsSenderException(String massage, LogicException.ExceptionType type, Exception ex) {
        super(massage, ex);
        this.type = type;
    }

    public SmsSenderException(String massage, LogicException.ExceptionType type) {
        super(massage);
        this.type = type;
    }

    @Override
    public LogicException.ExceptionType getType() {
        return type;
    }
}
