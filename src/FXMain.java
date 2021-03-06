import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author paulhemingway
 */
public class FXMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLFiles/Login.fxml"));
            
            Scene scene = new Scene(root);
            
            primaryStage.setTitle("Quiz-It");
            primaryStage.setMinHeight(400);
            primaryStage.setMinWidth(600);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) throws Exception{
        launch(args);
    }
}