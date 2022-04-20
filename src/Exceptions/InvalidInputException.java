/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package Exceptions;

/**
 *
 * @author paulhemingway
 */
public class InvalidInputException extends RuntimeException{

    
    public InvalidInputException() {
    }

    public InvalidInputException(String msg) {
        super(msg);
    }
    
    public InvalidInputException(String message, Throwable clause){
        super(message, clause);
    }
    
    public InvalidInputException(Throwable clause){
        super(clause);
    }
}
