
import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author paulhemingway
 */
public class Question {
    private String question;
    private String correctAnswer;
    private ArrayList<String> options = new ArrayList();
    private int questionNumber;
    
    //constructor for pushing question to the database
    public Question(String question, String correctAnswer, ArrayList<String> options){
        this.question = question;
        this.correctAnswer = correctAnswer;
        for(String i : options){
            this.options.add(i);
        }
    }
    
    // cosntructor for getting question from the database
    public Question(String question, String options, String correctAnswer){
        // get question number
       this.question = question;
       this.correctAnswer = correctAnswer;
       
       // options are loaded into the database separated by newline
       for (String i : options.split("\n")){
           this.options.add(i);
       }
       
    }
    
    public String getQuestion(){
        return this.question;
    }
   
    
    public String getAnswer(){
        return this.correctAnswer;
    }
    
    public ArrayList<String> getOptions(){
        return this.options;
    }
    
    public ArrayList<String> getOptionsShuffled(){
        ArrayList<String> shuffled = new ArrayList<String>(options);
        Collections.shuffle(shuffled);
        return shuffled;
    }
    
    public String getOptionsString(){
        String optionsString = "";
        for (String i : this.options){
            optionsString += (i + "\n");
        }
        return optionsString;
    }
}