package Exceptions;
/**
 *
 * @author paulhemingway
 */
public class EmptyFieldException extends RuntimeException{
    
    public EmptyFieldException() {
    }

    public EmptyFieldException(String msg) {
        super(msg);
    }
    
    public EmptyFieldException(String message, Throwable clause){
        super(message, clause);
    }
    
    public EmptyFieldException(Throwable clause){
        super(clause);
    }
}
