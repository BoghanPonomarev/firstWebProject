package ua.nure.ponomarev.exception;

/**
 * @author Bogdan_Ponamarev.
 */
public class MailSenderException extends LogicException{

    private LogicException.ExceptionType type;

    public MailSenderException(String massage, LogicException.ExceptionType type, Exception ex){
        super(massage,ex);
        this.type=type;
    }

    public MailSenderException(String massage, LogicException.ExceptionType type){
        super(massage);
        this.type = type;
    }

    @Override
    public ExceptionType getType() {
        return type;
    }
}
