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
    }
    
    //constructor for actually taking the quiz (storing into 'currentQuiz' static variable in Data class)
    public Quiz(Integer quizID, ArrayList<Question> questions, String title, int questionCount){
        this.quizID = quizID;
        this.questions = questions;
        this.title = title;
        this.questionCount = questionCount;
    }
    
    //constructor for populating quiz table in teacher view score scene
    public Quiz(Integer quizID, String title){
        this.quizID = quizID;
        this.title = title;
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
