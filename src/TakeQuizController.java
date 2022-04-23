/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * FXML Controller class
 *
 * @author paulhemingway
 */
public class TakeQuizController implements Initializable {

    @FXML
    private VBox optionsBox;
    @FXML
    private Label lbTitle;
    @FXML
    private Button btnSubmit;
    @FXML
    private Button btnPrevious;
    @FXML
    private Button btnNext;
    @FXML
    private ListView<String> questionNavigationList;
    @FXML
    private Button btnExit;
    
    ToggleGroup optionToggleGroup = new ToggleGroup();
    
    
    
    @FXML
    private Label lbQuestion;
    
    
    
    ArrayList<Question> questions = Data.currentQuiz.getQuestions();
    String[] userAnswers = new String[questions.size()];
    int currentQuestionNumber = 1;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (Question question : questions){
            
            questionNavigationList.getItems().add(Integer.toString(questions.indexOf(question) + 1));
        }
        lbTitle.setText(Data.currentQuiz.getTitle());
        updateQuestion(currentQuestionNumber);
        
    }    

    @FXML
    private void submitClicked(ActionEvent event) throws Exception {
        ArrayList<Integer> emptyList = new ArrayList<Integer>();
        boolean complete = true;
        for (int i = 0; i < questions.size(); i++ ){
            if(userAnswers[i] == null){
                complete = false;
                emptyList.add(i+1);
            }
        }
        
        if (!complete){
            String emptyString = "";
            for (int i = 0; i < emptyList.size(); i++){
                emptyString += (emptyList.get(i) + "  ");
                
            }
            AlertBox.display("You're not finished!", String.format("Not all questions have been answered:\n%s", emptyString));
        } else {
            
            int score = 0;
            for (int i = 0; i<questions.size(); i++){
                if(userAnswers[i].equals(questions.get(i).getAnswer())){
                    score++;
                }
            }
            
            String query = String.format("INSERT INTO scores (quizID, username, score) VALUES ('%d', '%s', '%d')", 
                    Data.currentQuiz.getQuizID(), Data.currentStudent.getUsername(), score);
            Database.post(query);
            
            AlertBox.display("Success!", "You have finished the quiz!\nAre you proud of yourself?");
            Data.currentQuiz = null;
            changeScenes("FXMLFiles/Home.fxml");
        }
    }

    @FXML
    private void previousClicked(ActionEvent event) {
        //addUserAnswer(currentQuestionNumber);
        updateQuestion(--currentQuestionNumber);
    }

    @FXML
    private void nextClicked(ActionEvent event) {
        //addUserAnswer(currentQuestionNumber);
        updateQuestion(++currentQuestionNumber);
        
    }

    @FXML
    private void exitClicked(ActionEvent event) {
        Data.currentQuiz = null;
    }
    
    public void updateQuestion(int questionNumber){
        
        currentQuestionNumber = questionNumber;
        // disable the back button if you're on the first question, and next button if on the last question
        btnPrevious.setDisable(currentQuestionNumber == 1);
        btnNext.setDisable(currentQuestionNumber == questions.size());
        
        questionNavigationList.getSelectionModel().clearAndSelect(questionNumber - 1);
        
        optionsBox.getChildren().clear();
        
        
        Question question = questions.get(currentQuestionNumber - 1);
        
        lbQuestion.setText(question.getQuestion());
        
        //https://www.tutorialspoint.com/how-to-create-a-radio-button-using-javafx
        // populating the options Vbox with radio buttons
        for (String option : question.getOptionsShuffled()){
            RadioButton button = new RadioButton(option);
            
            button.setToggleGroup(optionToggleGroup);
            button.setOnAction(event -> addUserAnswer(currentQuestionNumber));
            
            
            
            if(button.getText().equals(userAnswers[questionNumber-1])){
                button.setSelected(true);
            }
            
            optionsBox.getChildren().add(button);
            optionsBox.setMargin(button, new Insets(0, 0, 5, 0));;
            optionsBox.setPadding(new Insets(0, 0, 0, 20));
        }
    }
    
    public void addUserAnswer(int questionNumber){
        if(optionToggleGroup.getSelectedToggle() != null){
            RadioButton rb = (RadioButton)optionToggleGroup.getSelectedToggle();
            userAnswers[questionNumber-1] = rb.getText();
        }
        
    }

    @FXML
    private void listViewClicked(MouseEvent event) {
        
        String selected = questionNavigationList.getSelectionModel().getSelectedItem();
        if (selected != null){
            //addUserAnswer(currentQuestionNumber);
            updateQuestion(Integer.parseInt(selected));
        }
    }
    
    public void changeScenes(String filename) throws IOException{
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(filename));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //get the current stage information
        Stage window = (Stage)lbTitle.getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }

    
}
