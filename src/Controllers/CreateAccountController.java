import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Exceptions.*;

/**
 * FXML Controller class
 *
 * @author paulhemingway
 */
public class CreateAccountController implements Initializable {
    
    @FXML
    private Label lbFirstName;
    @FXML
    private TextField tfFirstName;
    @FXML
    private Label lbLastName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Label lbUsername;
    @FXML
    private Label lbPassword;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnCreateAccount;
    @FXML
    private ToggleGroup accountType;
    @FXML
    private RadioButton studentToggle;
    @FXML
    private RadioButton teacherToggle;
    @FXML
    private Label lbError;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void clearFields(){
        tfFirstName.clear();
        tfLastName.clear();
        tfUsername.clear();
        tfPassword.clear();
        accountType.selectToggle(null);
    }

    @FXML
    private void backToLogin(ActionEvent event) throws IOException {
        goToLogin();
    }

    @FXML
    private void createAccount(ActionEvent event) {
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        
        String errorMessage = new String();
        
        boolean valid = true;
        // must start with a letter, must be 5-18 characters, can only contain letters, numbers, and underscore
        final String usernamePattern = "^[A-Za-z]\\w{4,17}$";
        
        // must start/end with a letter, can contain '-,. and must be 1-25 characters
        final String namePattern = "(?i)[a-z]([- ',.a-z]{0,23}[a-z])?";
        
        // exceptions for input validation
        try{
            valid = true;
          
            if(!isValid(usernamePattern, username)){
                valid = false;
                throw new InvalidInputException("Username is invalid!\nMust be 5-18 characters.\nMust start with a letter.\nCan only contain letters, numbers, and underscores.\n");
            }
            if(tfPassword.getText().trim().isEmpty()){
                valid = false;
                throw new EmptyFieldException("Please input a password!");
            }
            if(tfPassword.getText().length() >= 24){
                valid = false;
                throw new InvalidInputException("Password must be 1-24 characters!");
            }
            if(Database.usernameExists(username)){
                valid = false;
                throw new NameTakenException("Username is already taken!\nPlease try again.");
            }
            if(!isValid(namePattern, firstName) || !isValid(namePattern, lastName)){
                valid = false;
                throw new InvalidInputException("First and/or last name is invalid!\nMust be 1-25 characters.\nMust start/end with a letter.\nCan contain ['-,.].");
            }
            if(accountType.getSelectedToggle() == null){
                valid = false;
                throw new EmptyFieldException("Please select account type!\nStudent or Teacher.");
            }
            
            if(valid){
                instantiate(username, password, firstName, lastName);
            }
        }
        catch (EmptyFieldException e){errorMessage = e.getMessage();}
        catch (NameTakenException e){errorMessage = e.getMessage();}
        catch (InvalidInputException e){errorMessage = e.getMessage();}
        catch (Exception e){errorMessage = e.getMessage();}
        
        finally {
            // set the error text to the first exception that was caught
            if (valid == false){
                lbError.setText(errorMessage);
            } else {
                lbError.setText("");
            }
        }
        
    }
    
    // see if the input matches the given pattern
    public boolean isValid(String pattern, String input){
        return input.matches(pattern);
    }
    
    public void instantiate(String username, String password, String firstName, String lastName) throws IOException{
        RadioButton rb = (RadioButton)accountType.getSelectedToggle();
        String selectedAccountType = rb.getText();
        Account account;
        
        if(selectedAccountType.equals("Teacher")){
            account = new Teacher(username, password, firstName, lastName);
        } else {
            account = new Student(username, password, firstName, lastName);
        }
        
        if(account.postAccount()){
            AlertBox.display("Success!", "Account successfully created!\nClick OK to login.");
            goToLogin();
        } else {
            AlertBox.display("Something is wrong...", "That didn't go as expected.");
        }
        
    }
    
    public void goToLogin() throws IOException{
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("FXMLFiles/Login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //get the current stage information
        Stage window = (Stage)tfUsername.getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
}