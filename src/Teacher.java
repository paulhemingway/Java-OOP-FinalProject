import java.util.ArrayList;

/**
 *
 * @author paulhemingway
 */
public class Teacher extends Account{

    private final String accountType = "Teacher";
    
    public Teacher(String username, String password, String firstName, String lastName){
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
    
    public boolean postQuiz(Quiz quiz){
        try {
            Database.postQuiz(quiz);
            return true;
        }
        catch (Exception e){System.out.println(e); System.out.println(e.getStackTrace());}
        return false;
    }
}