import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author paulhemingway
 */
public class Student extends Account{
    
    private final String accountType = "Student";
    
    public Student(String username, String password, String firstName, String lastName){
        super(username, password, firstName, lastName);
    }

    @Override
    public boolean postAccount() {
        try {
            String query = String.format("INSERT INTO accounts (username, password, firstName, lastName, accountType) VALUES ('%s', '%s', '%s', '%s', '%s')",
                    this.getUsername(), this.getPassword().replace("'","''"), firstName.replace("'","''"), lastName.replace("'","''"), this.accountType);
            Database.post(query);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    public void postScore(int score) throws Exception{
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
        LocalDateTime now = LocalDateTime.now();  
        
        String query = String.format("INSERT INTO scores (quizID, username, score, scoreDate) VALUES ('%d', '%s', '%d', '%s')", 
                    Data.currentQuiz.getQuizID(), this.getUsername(), score, dtf.format(now));
        Database.post(query);
    }
}
