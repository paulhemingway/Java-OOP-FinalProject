
import java.util.ArrayList;
/**
 *
 * @author paulhemingway
 */
public class Question {
    private String question;
    private String correctAnswer;
    private ArrayList<String> options = new ArrayList();
    
    public Question(String question, String correctAnswer, ArrayList<String> options){
        this.question = question;
        this.correctAnswer = correctAnswer;
        for(String i : options){
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
    
    public String getOptionsString(){
        String optionsString = "";
        for (String i : this.options){
            optionsString += (i + "\n");
        }
        return optionsString;
    }
    
}
