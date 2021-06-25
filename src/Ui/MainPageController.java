package Ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainPageController {
	
	
	@FXML
	private Button btn1;	
	@FXML
	private Button btn2;
	@FXML
	private Button btn3;
	@FXML
	private Button btn4;
	@FXML
	private Button btn5;
	@FXML
	private Button btn6;
	@FXML
	private Button btn7;
	@FXML
	private Button btn8;
	
	
	public void onButtonClicked(ActionEvent event) throws Exception{
		
		Node source=(Node) event.getSource();
		Stage prevStage=(Stage)source.getScene().getWindow();
		prevStage.close();
		
		if(event.getSource().equals(btn1)) {
			Parent root=FXMLLoader.load(getClass().getResource("addBranch.fxml"));
			
			  Stage stage = new Stage();
		        Scene scene = new Scene(root, 1100, 1100);
		        
		        stage.setScene(scene);
		        stage.setTitle("This is a new window");
		        stage.show();

			
		}
		
		 if(event.getSource().equals(btn2)) {
			Parent root=FXMLLoader.load(getClass().getResource("addCustomer.fxml"));
			
			  Stage stage = new Stage();
		        Scene scene = new Scene(root, 1100, 1100);
		        
		        stage.setScene(scene);
		        stage.setTitle("This is a new window");
		        stage.show();
			
		}
		 
		 if(event.getSource().equals(btn3)) {
			 Parent root=FXMLLoader.load(getClass().getResource("AddAmount.fxml"));
				
			  Stage stage = new Stage();
		        Scene scene = new Scene(root, 1100, 1100);
		        
		        stage.setScene(scene);
		        stage.setTitle("This is a new window");
		        stage.show();
			
		 }
		 
		 
		 
		 if(event.getSource().equals(btn4)) {
			 Parent root=FXMLLoader.load(getClass().getResource("withdrawAmount.fxml"));
				
			  Stage stage = new Stage();
		        Scene scene = new Scene(root, 1100, 1100);
		        
		        stage.setScene(scene);
		        stage.setTitle("This is a new window");
		        stage.show();
			
		 }
		 
		 
		 if(event.getSource().equals(btn5)) {
			 Parent root=FXMLLoader.load(getClass().getResource("searchCustomer.fxml"));
				
			  Stage stage = new Stage();
		        Scene scene = new Scene(root, 1100, 1100);
		        
		        stage.setScene(scene);
		        stage.setTitle("This is a new window");
		        stage.show();
			
		 }
		 
		 if(event.getSource().equals(btn6)) {
			 Parent root=FXMLLoader.load(getClass().getResource("deleteCustomer.fxml"));
				
			  Stage stage = new Stage();
		        Scene scene = new Scene(root, 1100, 1100);
		        
		        stage.setScene(scene);
		        stage.setTitle("This is a new window");
		        stage.show();
			
		 }
		 
		 if(event.getSource().equals(btn7)) {
			 Parent root=FXMLLoader.load(getClass().getResource("viewTransactions.fxml"));
				
			  Stage stage = new Stage();
		        Scene scene = new Scene(root, 1100, 1100);
		        
		        stage.setScene(scene);
		        stage.setTitle("This is a new window");
		        stage.show();
			
		 }
		 
		 if(event.getSource().equals(btn8)) {
			return;
			
		 }
		 
		 
		 
	}
	
	

}
