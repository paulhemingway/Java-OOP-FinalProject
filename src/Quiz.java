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
    private Integer quizID;
    
    //constructor to store it into the database
    public Quiz(String title, String author, ArrayList<Question> questions){
        this.title = title;
        this.author = author;
        this.questions = questions;
        this.questionCount = questions.size();
    }
    
    // constructor for getting quizzes from the database
    public Quiz(Integer quizID, String author, String title){
        this.quizID = quizID;
        this.author = author;
        this.title = title;
        System.out.println("quizCreated");
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public String getAuthor(){
        return this.author;
    }
    
    public Integer getQuizID(){
        return this.quizID;
    }
    
    public ArrayList<Question> getQuestions(){
        return this.questions;
    }
    
}
