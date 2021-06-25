package Ui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addAmount_Controller {
	
	
	
	@FXML
	private TextField branch_id;
	
	@FXML
	private TextField customer_phone;
	
	@FXML
	private TextField add_amount;
	
	@FXML
	private Button submit_addAmount;
	
	@FXML
	private Button exit;
	
	@FXML
	private Label result;
	
	@FXML
	public void initialize() {
		submit_addAmount.setDisable(true);
	}
	
	@FXML
	public void onButtonClicked(ActionEvent event) {
		
		
		try(
				Socket socket=new Socket("localhost", 5000);
				DataOutputStream dout=new DataOutputStream(socket.getOutputStream());
				DataInputStream din=new DataInputStream(socket.getInputStream())
				){
			
			dout.writeUTF(submit_addAmount.getId());
			
			int branchID=Integer.parseInt(branch_id.getText());
			String customerPhone=customer_phone.getText();
			double addAmount=Double.parseDouble(add_amount.getText());
			
			dout.writeInt(branchID);
			dout.writeUTF(customerPhone);
			dout.writeDouble(addAmount);
			
			
			result.setText(din.readUTF());
					
			
		}catch(IOException e) {
			System.out.println("IOException e: "+e.getMessage());
		}
		
	
		
		
		
	}
	
	@FXML
	public void onExit(ActionEvent event) throws Exception{
		Node source=(Node) event.getSource();
		Stage prevStage=(Stage)source.getScene().getWindow();
		prevStage.close();
		
		
		Parent root=FXMLLoader.load(getClass().getResource("MainPage.fxml"));
		
		  Stage stage = new Stage();
	        Scene scene = new Scene(root, 1100, 1100);
	        
	        stage.setScene(scene);
	        stage.setTitle("Main Page");
	        stage.show();
		
	}
	
	
	@FXML
	public void handleKeyReleased() {
		String branchId=branch_id.getText();
		
		String customerPhone=customer_phone.getText();
		String addAmount=add_amount.getText();
		
		boolean disabled= branchId.isEmpty() || addAmount.isEmpty() ||
					customerPhone.isEmpty() || customerPhone.trim().isEmpty();
				
		submit_addAmount.setDisable(disabled);
	}
	
	

}
