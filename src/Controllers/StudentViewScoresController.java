import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
public class StudentViewScoresController implements Initializable {

    @FXML
    private TableView<Score> scoreTable;
    ObservableList<Score> scores;
    @FXML
    private TableColumn<Score, String> teacherCol;
    @FXML
    private TableColumn<Score, String> quizCol;
    @FXML
    private TableColumn<Score, String> dateCol;
    @FXML
    private TableColumn<Score, Double> scoreCol;
    @FXML
    private Button btnBack;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // can't select from this table
        scoreTable.setSelectionModel(null);
        fillTable();
    }    
    
    // assign each column to a property of score in order to fill all the data from an observable list
    public void fillTable() {
        try {
            scores = getScores();
            teacherCol.setCellValueFactory(new PropertyValueFactory("teacher"));
            quizCol.setCellValueFactory(new PropertyValueFactory("title"));
            dateCol.setCellValueFactory(new PropertyValueFactory("date"));
            scoreCol.setCellValueFactory(new PropertyValueFactory("score"));
            scoreTable.getColumns().clear();
            scoreTable.setItems(scores);
            
            scoreTable.getColumns().addAll(teacherCol, quizCol, dateCol, scoreCol);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private ObservableList<Score> getScores() throws Exception{
        
        String query = String.format("SELECT a.lastName, q.title, s.scoreDate, ROUND((s.score / q.possibleScore), 2) as 'score' " +
            "FROM accounts a, quizzes q, scores s " +
            "WHERE s.quizID = q.quizID AND a.username = q.author AND s.username = '%s'", 
                Data.currentStudent.getUsername());
        
        ArrayList<HashMap<String, Object>> scoreMap = Database.getAll(query);
        
        ArrayList<Score> scores = new ArrayList();
        
        for (HashMap i : scoreMap){
            Score score = new Score((String)i.get("lastName"), (String)i.get("title"), (BigDecimal)i.get("score"), (Date)i.get("scoreDate"));
            
            scores.add(score);
        }
        
        return FXCollections.observableArrayList(scores);
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException{
        changeScenes("FXMLFiles/Home.fxml");
    }
    
    public void changeScenes(String filename) throws IOException{
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(filename));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //get the current stage information
        Stage window = (Stage)btnBack.getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
    
}
