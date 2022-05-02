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
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author paulhemingway
 */
public class HomeController implements Initializable {

    @FXML
    private Label welcome;
    @FXML
    private Button btnCreateQuiz;
    @FXML
    private Button btnViewQuizScores;
    @FXML
    private Button btnTakeQuiz;
    @FXML
    private Button btnViewMyScores;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String greeting = "";
        if(Data.isTeacher){
            Teacher currentTeacher = Data.currentTeacher;
            greeting = String.format("Welcome, %s %s!", Data.currentTeacher.getFirstName(), Data.currentTeacher.getLastName());
        } else {
            Student currentStudent = Data.currentStudent;
            greeting = String.format("Welcome, %s %s!", Data.currentStudent.getFirstName(), Data.currentStudent.getLastName());
        }
        welcome.setText(greeting);
        
        btnCreateQuiz.setVisible(Data.isTeacher);
        btnViewQuizScores.setVisible(Data.isTeacher);
        btnTakeQuiz.setVisible(Data.isStudent);
        btnViewMyScores.setVisible(Data.isStudent);
    }    
    
    public void changeScenes(String filename) throws IOException{
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(filename));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //get the current stage information
        Stage window = (Stage)welcome.getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void goToCreateQuizScene(ActionEvent event) throws IOException{
        changeScenes("FXMLFiles/CreateQuiz.fxml");
    }

    @FXML
    private void goToViewQuizScores(ActionEvent event) throws IOException{
        changeScenes("FXMLFiles/TeacherViewScores.fxml");
    }

    @FXML
    private void takeQuiz(ActionEvent event) throws IOException{
        changeScenes("FXMLFiles/ChooseQuiz.fxml");
    }

    @FXML
    private void viewMyScores(ActionEvent event) throws IOException{
        changeScenes("FXMLFiles/StudentViewScores.fxml");
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException{
        Data.clearCurrent();
        changeScenes("FXMLFiles/Login.fxml");
    }
}