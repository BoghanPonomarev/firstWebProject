package ua.nure.ponomarev.web.exception;

/**
 * @author Bogdan_Ponamarev.
 */
public class DBException extends LogicException {


    private LogicException.ExceptionType type;

    public DBException(String massage, LogicException.ExceptionType type, Exception ex){
        super(massage,ex);
        this.type=type;
    }

    /**
     * Constructor for server type exceptions , because all have one massage
     * @param ex exception that was thrown
     */
    public DBException(Exception ex){
        super("Dear user, now we have some troubles with site!\n" + "We give apologise, try to come back later",ex);
        this.type= LogicException.ExceptionType.SERVER_EXCEPTION;
    }

    @Override
    public ExceptionType getType() {
        return type;
    }
}
