/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

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
public class TeacherViewScores implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private Button btnViewScores;
    @FXML
    private TableView<Quiz> quizTable;
    @FXML
    private TableColumn<Quiz, Integer> quizIDCol;
    @FXML
    private TableColumn<Quiz, String> titleCol;
    @FXML
    private TableView<Score> scoreTable;
    @FXML
    private TableColumn<Score, String> studentCol;
    @FXML
    private TableColumn<Score, Double> scoreCol;

    ObservableList<Score> scores;
    ObservableList<Quiz> quizzes;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scoreTable.setSelectionModel(null);
        fillQuizTable();
    }    

    @FXML
    private void goBack(ActionEvent event) throws Exception{
        changeScenes("FXMLFiles/Home.fxml");
    }
    
    public void fillQuizTable(){
        try {
            quizzes = getQuizzes();
            quizIDCol.setCellValueFactory(new PropertyValueFactory("quizID"));
            titleCol.setCellValueFactory(new PropertyValueFactory("title"));
            quizTable.getColumns().clear();
            quizTable.setItems(quizzes);
            
            quizTable.getColumns().addAll(quizIDCol, titleCol);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void fillScoreTable(int quizID){
        scoreTable.getItems().clear();
        try {
            scores = getScores(quizID);
            studentCol.setCellValueFactory(new PropertyValueFactory("student"));
            scoreCol.setCellValueFactory(new PropertyValueFactory("score"));
            scoreTable.getColumns().clear();
            scoreTable.setItems(scores);
            
            scoreTable.getColumns().addAll(studentCol, scoreCol);
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
    }
    
    public ObservableList<Quiz> getQuizzes() throws Exception{
        String query = String.format("SELECT quizID, title from quizzes where author = '%s'", 
                Data.currentTeacher.getUsername());
        
        ArrayList<HashMap<String, Object>> quizMap = Database.getAll(query);
        
        ArrayList<Quiz> quizzes = new ArrayList();
        
        for (HashMap i : quizMap){
            Quiz quiz = new Quiz((Integer)i.get("quizID"), (String)i.get("title"));
            
            quizzes.add(quiz);
        }
        
        return FXCollections.observableArrayList(quizzes);
    }
    
    public ObservableList<Score> getScores(int quizID) throws Exception{
        String query = String.format("SELECT a.firstName, a.lastName,  MAX(ROUND((s.score / "+
                "q.possibleScore),2)) as 'score' from quizzes q, scores s, accounts a where "+
                "q.author = '%s' AND s.quizID = q.quizID AND a.username = s.username AND s.quizID = '%d'"+ 
                "GROUP BY s.username", Data.currentTeacher.getUsername(), quizID);
        
        ArrayList<HashMap<String, Object>> scoreMap = Database.getAll(query);
        
        ArrayList<Score> scores = new ArrayList();
        
        for (HashMap i : scoreMap){
            Score score = new Score((String)i.get("firstName"), (String)i.get("lastName"), (BigDecimal)i.get("score"));
            
            scores.add(score);
        }
        
        return FXCollections.observableArrayList(scores);
    }
    
    public void changeScenes(String filename) throws IOException{
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(filename));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //get the current stage information
        Stage window = (Stage)btnBack.getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void viewScores(ActionEvent event) {
        if (quizTable.getSelectionModel().getSelectedItem() != null){
            Quiz quiz = quizTable.getSelectionModel().getSelectedItem();
            fillScoreTable(quiz.getQuizID());
        }
    }
}
