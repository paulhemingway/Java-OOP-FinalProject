import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author paulhemingway
 */
public class LoginController implements Initializable {

    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnSignUp;
    @FXML
    private TextField inputUsername;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private Label lbError;
    @FXML
    private Button btnExit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void checkLogin(ActionEvent event) throws Exception {
        String errorMessage = "";
        lbError.setText("");
        String username = inputUsername.getText();
        String password = inputPassword.getText();
        
        String[] x = Database.checkLogin(username, password);
        
        // if it only brought back 1 string, that string will be an error message. 
        if(x.length == 1){
            errorMessage = x[0];
        } else {
            // check x[4], which is the account type. 
            if (x[4].equals("Teacher")){
                Data.currentTeacher = new Teacher(x[0], x[1], x[2], x[3]);
                Data.isTeacher = true;
            } else {
                Data.currentStudent = new Student(x[0], x[1], x[2], x[3]);
                Data.isStudent = true;
            }
            changeScenes("FXMLFiles/Home.fxml");
        }
        lbError.setText(errorMessage);
    }

    @FXML
    private void goToCreateAccount(ActionEvent event) throws IOException{
        changeScenes("FXMLFiles/CreateAccount.fxml");
    }
    
    public void changeScenes(String filename) throws IOException{
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(filename));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //get the current stage information
        Stage window = (Stage)inputUsername.getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void exitProgram(ActionEvent event) {
        Platform.exit();
    }
    
}
