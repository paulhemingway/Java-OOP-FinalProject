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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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
    @FXML
    private VBox vbox1;
    @FXML
    private VBox vbox2;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorMessage = "";
    }    

    // remove quiz from the listview as well as the arraylist
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
            // check for empty fields
            if(emptyInput(tfTitle) || emptyInput(tfQuestion) || emptyInput(tfCorrectAnswer) || emptyInput(tfOption1)){
                throw new InvalidInputException("Please fill out the required fields.");
            }
            
            // check title length. if it exceeds 50, set border color to red
            if(tfTitle.getText().length() > 50){
                tfTitle.setStyle("-fx-border-color: red;");
                throw new InvalidInputException("Title must not exceed 50 characters!");
            } else {
                tfTitle.setStyle("-fx-border-color: none;");
            }
            
            // create an array of the textfields to iterate through and add it to the options arraylist
            TextField[] optionsArray = new TextField[]{tfCorrectAnswer, tfOption1, tfOption2, tfOption3};
            
            for (TextField i : optionsArray){
                if(!i.getText().trim().isEmpty()){
                    if(i.getText().length() > 150){
                        tfTitle.setStyle("-fx-border-color: red;");
                        throw new InvalidInputException("Options must not exceed 150 characters!");
                    } else {
                        tfTitle.setStyle("-fx-border-color: none;");
                    }
                    options.add(i.getText());
                    lbError.setText("");
                }
            }
            
            question = new Question(tfQuestion.getText(), tfCorrectAnswer.getText(), options);
            
            //add question to both the arraylist and the listview
            questions.add(question);
            lvQuestionList.getItems().add(question.getQuestion());
            clearAll();
        }
        catch (InvalidInputException e){errorMessage = e.getMessage();}
        
        lbError.setText(errorMessage);
    }

    @FXML
    private void CreateQuiz(ActionEvent event) throws IOException{
        try {
            if(questions.isEmpty()){
                throw new EmptyQuestionsException("You must have at least 1 question!\nIs it even a quiz?");
            }
            // add parameters
            Quiz quiz = new Quiz(tfTitle.getText(), Data.currentTeacher.getUsername(), questions);
            if(Data.currentTeacher.postQuiz(quiz)){
                AlertBox.display("Success!", "Your quiz has been created!");
                changeScenes("FXMLFiles/Home.fxml");
            }
        }
        catch(EmptyQuestionsException e){AlertBox.display("No questions!", e.getMessage());}
    }
    
    // check if it's empty. if it is, set the border color to red
    public boolean emptyInput(TextInputControl x){
        if(x.getText().trim().isEmpty()){
            x.setStyle("-fx-border-color: red;");
            return true;
        }
        x.setStyle("-fx-border-color: none;");
        return false;
    }
    
    // this is called when a question is added
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