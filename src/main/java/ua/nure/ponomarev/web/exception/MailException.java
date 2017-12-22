package ua.nure.ponomarev.web.exception;

/**
 * @author Bogdan_Ponamarev.
 */
public class MailException extends LogicException{

    private LogicException.ExceptionType type;

    public MailException(String massage, LogicException.ExceptionType type, Exception ex){
        super(massage,ex);
        this.type=type;
    }

    public MailException(String massage,LogicException.ExceptionType type){
        super(massage);
        this.type = type;
    }

    @Override
    public ExceptionType getType() {
        return type;
    }
}
