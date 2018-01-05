package ua.nure.ponomarev.exception;

/**
 * @author Bogdan_Ponamarev.
 */
public class SMSSenderException extends LogicException{
    private LogicException.ExceptionType type;

    public SMSSenderException(String massage, LogicException.ExceptionType type, Exception ex){
        super(massage,ex);
        this.type=type;
    }

    public SMSSenderException(String massage, LogicException.ExceptionType type){
        super(massage);
        this.type = type;
    }

    @Override
    public LogicException.ExceptionType getType() {
        return type;
    }
}
