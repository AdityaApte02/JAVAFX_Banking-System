package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;


public class Main extends Application  {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root=FXMLLoader.load(getClass().getResource("/Ui/MainPage.fxml"));
		
				
	   primaryStage.setTitle("Hello JavaFX!");
       primaryStage.setScene(new Scene(root, 1100, 1100));
       primaryStage.show();

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
