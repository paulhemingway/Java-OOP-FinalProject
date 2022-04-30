import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
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
            
            // set the columns to specific properties of the quiz class
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
    
    // returns an observable list to populate the table view
    private ObservableList<Quiz> getQuizzes() throws Exception{
        // this method returns an arraylist of a hashmap. Basically an arraylist of records
        ArrayList<HashMap<String, Object>> quizMap = Database.getAll("SELECT q.quizID, q.title, a.lastName FROM quizzes q, accounts a WHERE q.author = a.username");
        ArrayList<Quiz> quizzes = new ArrayList();
        
        // for each hashmap (record) in the arraylist, create a quiz object out of it 
        // and add that quiz to the quizzes arraylist
        for (HashMap i : quizMap){
            Quiz quiz = new Quiz((Integer)i.get("quizID"), (String)i.get("lastName"), (String)i.get("title"));
            
            quizzes.add(quiz);
        }
        
        // convert arraylist to observable list and return it
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
        // get current selected quiz on the table view and make that the current quiz
        Quiz quiz = quizTable.getSelectionModel().getSelectedItem();
        
        if(quiz != null){
            // get all questions from the database
            ArrayList<Question> questions = Database.getQuestions(quiz.getQuizID());

            
            Data.currentQuiz = new Quiz(quiz.getQuizID(), questions, quiz.getTitle(), questions.size());
            changeScenes("FXMLFiles/TakeQuiz.fxml");
        } 
    }
}
