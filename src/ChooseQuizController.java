import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author paulhemingway
 */
public class ChooseQuizController implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private Button btnBegin;
    @FXML
    private TableView<Quiz> quizTable;
    
    ObservableList<Quiz> quizzes;
    @FXML
    private TableColumn<Quiz, Integer> quizIDColumn;
    @FXML
    private TableColumn<Quiz, String> titleColumn;
    @FXML
    private TableColumn<Quiz, String> teacherColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTable();
    }    
    
    public void fillTable() {
        try {
            quizzes = getQuizzes();
            quizIDColumn.setCellValueFactory(new PropertyValueFactory("quizID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory("title"));
            teacherColumn.setCellValueFactory(new PropertyValueFactory("author"));
            quizTable.getColumns().clear();
            quizTable.setItems(quizzes);
            
            quizTable.getColumns().addAll(quizIDColumn, titleColumn, teacherColumn);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException{
        changeScenes("FXMLFiles/Home.fxml");
    }
    
    private ObservableList<Quiz> getQuizzes() throws Exception{
        ArrayList<HashMap<String, Object>> quizMap = Database.getAll("quizzes");
        ArrayList<Quiz> quizzes = new ArrayList();
        
        for (HashMap i : quizMap){
            Quiz quiz = new Quiz((Integer)i.get("quizID"), (String)i.get("author"), (String)i.get("title"));
            
            quizzes.add(quiz);
        }
        
        return FXCollections.observableArrayList(quizzes);
    }
    
    public void changeScenes(String filename) throws IOException{
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(filename));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //get the current stage information
        Stage window = (Stage)quizTable.getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void takeQuiz(ActionEvent event) throws Exception {
        Quiz quiz = quizTable.getSelectionModel().getSelectedItem();
       
        // get all questions from the database
        ArrayList<Question> questions = Database.getQuestions(quiz.getQuizID());
        
        Data.currentQuiz = new Quiz(quiz.getQuizID(), questions, quiz.getTitle(), questions.size());
        changeScenes("FXMLFiles/TakeQuiz.fxml");
    }
}
