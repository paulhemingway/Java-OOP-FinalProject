import java.util.ArrayList;
/**
 *
 * @author paulhemingway
 */
public class Quiz {
    private String title; 
    private String author;
    private ArrayList<Question> questions;
    private int questionCount, quizID;
    
    //constructor to store it into the database
    public Quiz(String title, String author, ArrayList<Question> questions){
        this.title = title;
        this.author = author;
        this.questions = questions;
        this.questionCount = questions.size();
    }
    
    // constructor for getting quizzes from the database
    public Quiz(int quizID, String author, String title){
        this.quizID = quizID;
        this.author = author;
        this.title = title;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public String getAuthor(){
        return this.author;
    }
    
    public ArrayList<Question> getQuestions(){
        return this.questions;
    }
    
}
