/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

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
    
    Student currentStudent;
    Teacher currentTeacher;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String greeting = "";
        if(Data.isTeacher){
            currentTeacher = (Teacher)Data.currentTeacher;
            greeting = String.format("Welcome, %s %s!", currentTeacher.getFirstName(), currentTeacher.getLastName());
        } else {
            currentStudent = (Student)Data.currentStudent;
            greeting = String.format("Welcome, %s %s!", currentStudent.getFirstName(), currentStudent.getLastName());
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
    private void goToViewQuizScores(ActionEvent event) {
    }

    @FXML
    private void takeQuiz(ActionEvent event) {
    }

    @FXML
    private void viewMyScores(ActionEvent event) {
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException{
        Data.clearCurrent();
        changeScenes("FXMLFiles/Login.fxml");
    }
    
    


}
