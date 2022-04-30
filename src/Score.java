import java.math.BigDecimal;
import java.util.Date;
/**
 *
 * @author paulhemingway
 */
public class Score {
    private String teacher, title, date, student;
    private double score;

    //constructor for student to view their score
    public Score(String teacher, String title, BigDecimal score, Date date){
        this.teacher = teacher;
        this.title = title;
        this.score = score.doubleValue() * 100;
        this.date = date.toString();
    }
    
    // constructor to view their students' scores
    public Score(String firstName, String lastName, BigDecimal score){
        this.score = score.doubleValue() * 100;
        this.student = firstName + " " + lastName;
    }
    
    public String getTeacher(){
        return teacher;
    }
    
    public String getTitle(){
        return title;
    }
    
    public double getScore(){
        return score;
    }
    
    public String getDate(){
        return date;
    }
    
    public String getStudent(){
        return student;
    }
    
}
