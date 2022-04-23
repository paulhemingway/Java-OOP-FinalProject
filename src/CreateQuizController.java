/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Exceptions.*;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author paulhemingway
 */
public class CreateQuizController implements Initializable {

    @FXML
    private TextField tfTitle;
    @FXML
    private TextArea tfQuestion;
    @FXML
    private TextField tfCorrectAnswer;
    @FXML
    private TextField tfOption1;
    @FXML
    private TextField tfOption2;
    @FXML
    private TextField tfOption3;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAddQuestion;
    @FXML
    private Button btnCreateQuiz;
    @FXML
    private Label lbError;
    @FXML
    private ListView<String> lvQuestionList;
    
    String errorMessage = "";
    ArrayList<Question> questions = new ArrayList();
    @FXML
    private Button btnBack;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void deleteQuestion(ActionEvent event) {
        var selected = lvQuestionList.getSelectionModel().getSelectedItem();
        
        for(Question i : questions){
            if (i.getQuestion().equals(selected)){
                questions.remove(i);
                break;
            }
        }
        lvQuestionList.getItems().remove(selected);
        
    }

    @FXML
    private void addQuestion(ActionEvent event) {
        lbError.setText("");
        Question question = null;
        ArrayList<String> options = new ArrayList<String>();
        try {

            if(emptyInput(tfTitle) || emptyInput(tfQuestion) || emptyInput(tfCorrectAnswer) || emptyInput(tfOption1)){
                throw new InvalidInputException("Please fill out the required fields.");
            }
            TextField[] optionsArray = new TextField[]{tfCorrectAnswer, tfOption1, tfOption2, tfOption3};
            
            for (TextField i : optionsArray){
                if(!i.getText().trim().isEmpty()){
                    options.add(i.getText());
                }
            }
            
            question = new Question(tfQuestion.getText(), tfCorrectAnswer.getText(), options);
            questions.add(question);

            lvQuestionList.getItems().add(question.getQuestion());
            clearAll();
        }
        catch (InvalidInputException e){errorMessage = e.getMessage();}
        
        lbError.setText(errorMessage);
    }

    @FXML
    private void CreateQuiz(ActionEvent event) {
        try {
            if(questions.isEmpty()){
                throw new EmptyQuestionsException("You must have at least 1 question! It can't be THAT easy to pass!");
            }
            
            // add parameters
            Quiz quiz = new Quiz(tfTitle.getText(), Data.currentTeacher.getUsername(), questions);
            if(Data.currentTeacher.postQuiz(quiz)){
                System.out.println("Quiz created.");
            }
        }
        catch(EmptyQuestionsException e){AlertBox.display("No questions!", e.getMessage());}
    }
    
    public boolean emptyInput(TextInputControl x){
        if(x.getText().trim().isEmpty()){
            x.setStyle("-fx-border-color: red;");
            return true;
        }
        x.setStyle("-fx-border-color: none;");
        return false;
    }
    
    public void clearAll(){
        tfQuestion.clear();
        tfCorrectAnswer.clear();
        tfOption1.clear();
        tfOption2.clear();
        tfOption3.clear();
    }

    public void changeScenes(String filename) throws IOException{
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(filename));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //get the current stage information
        Stage window = (Stage)tfQuestion.getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException{
        questions.clear();
        errorMessage = "";
        changeScenes("FXMLFiles/Home.fxml");
    }
    
}
