import java.util.*;
/**
 *
 * @author paulhemingway
 */
public class Student extends Account{
    
    private final String accountType = "Student";
    Map scores = new HashMap();
    
    public Student(String username, String password, String firstName, String lastName){
        super(username, password, firstName, lastName);
    }

    @Override
    public boolean postAccount() {
        try {
            String query = String.format("INSERT INTO accounts (username, password, firstName, lastName, accountType) VALUES ('%s', '%s', '%s', '%s', '%s')",
                    this.getUsername(), this.getPassword().replace("'","''"), firstName.replace("'","''"), lastName.replace("'","''"), this.accountType);
            System.out.println(query);
            Database.post(query);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    
    public void getScores(){
        
    }
}
