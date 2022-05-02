/**
 *
 * @author paulhemingway
 */
public abstract class Account {
    private String username, password;
    protected String firstName, lastName;
    
    public Account(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password; 
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public String getUsername() {
        return this.username; 
    }
    
    public String getPassword(){
        return this.password; 
    }
    
    // the subclasses will have slightly different queries to insert into database
    public abstract boolean postAccount();
    
    public String getFirstName(){
        return this.firstName;
    }
    
    public String getLastName(){
        return this.lastName;
    }
}