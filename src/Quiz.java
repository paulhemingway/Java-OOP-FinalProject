import java.util.ArrayList;
/**
 *
 * @author paulhemingway
 */
public class Quiz {
    private String title; 
    private String author;
    private ArrayList<Question> questions;
    private int questionCount;
    
    public Quiz(String title, String author, ArrayList<Question> questions){
        this.title = title;
        this.author = author;
        this.questions = questions;
        this.questionCount = questions.size();
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
