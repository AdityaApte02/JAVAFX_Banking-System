package Ui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import backend.Customer;
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

public class addCustomer_Controller {
	
	
	
	@FXML
	private TextField branch_id;
	
	@FXML
	private TextField customer_name;
	
	
	@FXML
	private TextField customer_phone;
	
	
	@FXML
	private TextField customer_address;
	
	@FXML
	private TextField customer_age;
	
	@FXML
	private Button submit_customer;
	
	@FXML
	private Button exit;
	
	@FXML
	private Label result;
	
	@FXML
	public void initialize() {
		submit_customer.setDisable(true);
	}
	
	@FXML
	public void onButtonClicked(ActionEvent event) {
	
		try(Socket socket=new Socket("localhost", 5000);
				DataInputStream din=new DataInputStream(socket.getInputStream());
				DataOutputStream dout=new DataOutputStream(socket.getOutputStream());
						){
			
			System.out.println(submit_customer.getId());
			dout.writeUTF(submit_customer.getId());
			
			int branchID=Integer.parseInt(branch_id.getText());
			dout.writeInt(branchID);
			
			String customerName=customer_name.getText();
			String customerPhone=customer_phone.getText();
			String customerAddress=customer_address.getText();
			int customerAge=Integer.parseInt(customer_age.getText());
		
			System.out.println(branchID+" "+ customerName+" "+ customerPhone+" "+ customerAddress+" "+ customerAge);
			
			Customer customer=new Customer(customerName, customerPhone, customerAddress, customerAge);
			ObjectOutputStream oout=new ObjectOutputStream(socket.getOutputStream());
			oout.writeObject(customer);
			
			result.setText(din.readUTF());
			
						
			
			
		}catch(IOException e) {
			System.out.println("IOException: "+e.getMessage());
		}
		
		
		
		
	}
	
	@FXML
	public void onExit(ActionEvent event)throws Exception {
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
		String customerName=customer_name.getText();
		String customerPhone=customer_phone.getText();
		String customerAddress=customer_address.getText();
		String customerAge=customer_age.getText();
		boolean disabled= branchId.isEmpty() || customerAge.isEmpty() ||
				customerName.isEmpty() || customerName.trim().isEmpty() ||
				customerPhone.isEmpty() || customerPhone.trim().isEmpty() ||
				customerAddress.isEmpty() || customerAddress.trim().isEmpty();
		submit_customer.setDisable(disabled);
	}
	
	
	

}
