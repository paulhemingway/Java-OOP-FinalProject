/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package Exceptions;

/**
 *
 * @author paulhemingway
 */
public class EmptyQuestionsException extends RuntimeException{

    
    public EmptyQuestionsException() {
    }

    public EmptyQuestionsException(String msg) {
        super(msg);
    }
    
    public EmptyQuestionsException(String message, Throwable clause){
        super(message, clause);
    }
    
    public EmptyQuestionsException(Throwable clause){
        super(clause);
    }
}
