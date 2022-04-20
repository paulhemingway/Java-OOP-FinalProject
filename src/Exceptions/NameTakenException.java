package Exceptions;

/**
 *
 * @author paulhemingway
 */
public class NameTakenException extends RuntimeException{
    
    public NameTakenException() {
    }

    public NameTakenException(String msg) {
        super(msg);
    }
    
    public NameTakenException(String message, Throwable clause){
        super(message, clause);
    }
    
    public NameTakenException(Throwable clause){
        super(clause);
    }
}
