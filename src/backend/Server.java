package backend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class Server {
	

public static void main(String[] args) throws IOException, ClassNotFoundException, EOFException{
	System.out.println("Hello");
		
	Class.forName("com.mysql.cj.jdbc.Driver");
	
	DataSource dataSource=new DataSource();
	
	if(dataSource.open()) {
		dataSource.createBranchTable();
		dataSource.createCustomerTable();
		dataSource.createTransactionTable();
		
		System.out.println("Database created successfully");
	}else {
		System.out.println("Database couldn't be created");
	}
		

	ServerSocket serversocket=new ServerSocket(5000);
	
	
		DataOutputStream dout;
		ObjectInputStream oin;
		ObjectOutputStream oout;
	
		
		while(true) {
			
			Socket socket=serversocket.accept();
			System.out.println("Client Connected");
			
			String option;
			
			DataInputStream din = new DataInputStream(socket.getInputStream());
			  option=din.readUTF();
			  System.out.println("option: "+option);
			
			  switch(option) {
			  
			  case "submit_branchName":
			  {
				 
					  dout=new DataOutputStream(socket.getOutputStream());
					  String branchName=din.readUTF();
					  System.out.println("branchName from Server: "+branchName);
					  if(dataSource.addBranch(branchName) >= 0) {
						  dout.writeUTF(branchName+" Added Successfully");
					  }else {
						  dout.writeUTF(branchName+" already exists");
					  }
					  						  
				  
				  break;
			  }
			
			  case "submit_customer":
			  {
				  System.out.println("popuauau");
				  dout=new DataOutputStream(socket.getOutputStream());
				  
				  
				  int branch_id=din.readInt();
				  
				  oin=new ObjectInputStream(socket.getInputStream());
				  
				  Customer customer=(Customer)oin.readObject();
				  
				  System.out.println(customer.getAddress());
				  
				  if(dataSource.addCustomer(customer.getName(), customer.getPhone(), customer.getAddress(), customer.getAge(), branch_id) >= 0) {
					  dout.writeUTF("The customer "+customer.getName()+
			                     " has been added");
				  }else {
					  dout.writeUTF("Enter valid Phone Number \n Cannot add customer");
				  }
				  break;
			  }
			 
			  case "submit_addAmount":
			  {
				  dout=new DataOutputStream(socket.getOutputStream());
				  				 
				  int branch_id=din.readInt();
				  String phone=din.readUTF();
				  double addAmount=din.readDouble();
				  
				  if(dataSource.addAmount(phone, addAmount, branch_id) >= 0) {
					  
					  dout.writeUTF( "The customer with phone number "+phone+" has done an additional transaction of " +
                  addAmount);
				  }else {
					  dout.writeUTF("Customer not Found..\nCannot make transactions");
				  }
				  
				  break;
				  
			  }
			  
			  case "submit_withdrawAmount":
			  {
				  dout=new DataOutputStream(socket.getOutputStream());
				  
				  int branch_id=din.readInt();
				  String phone=din.readUTF();
				  double withdrawAmount=din.readDouble();
				  
				  if(dataSource.WithdrawAmount(phone, withdrawAmount, branch_id) >= 0) {
					  
					  dout.writeUTF( "The customer with phone number "+phone+" withdrew an amount of " +
                  withdrawAmount);
				  }else {
					  dout.writeUTF("Customer not Found..\nCannot make transactions");
				  }
				  
				  break;
			  }
			  
			  case "submit_searchCustomer":
			  {
				  dout=new DataOutputStream(socket.getOutputStream());
				 
				 
				  String CustomerName=din.readUTF();
				  
				  String phone=din.readUTF();
				  
				  if(dataSource.queryCustomer(phone)!=null) {
					  dout.writeUTF("Customer with name "+CustomerName+" and phone number "+
				  phone+" Found");
				  }else {
					  dout.writeUTF("Record not Found");
				  }
				  
				  break;
			  }
			  
			  case "submit_deleteCustomer":
			  {
				 				  
				  dout=new DataOutputStream(socket.getOutputStream());
				 
				  String phone=din.readUTF();
				  
				  if(dataSource.deleteCusomer(phone) >= 0) {
					  dout.writeUTF("Customer with phone number "+phone+
				  " Deleted Successfully");
				  }else {
					  dout.writeUTF("Record not Found");
				  }
				  
				
				  break;
				  
			  }
			  
			  case "submit_viewTransactions":
			  {
				  dout=new DataOutputStream(socket.getOutputStream());
				  String phone=din.readUTF();
				  
				  List<Transaction> list=dataSource.viewTransactions(phone);
				  
				  dout.writeInt(list.size());
				  
				 
				
				for(Transaction transaction:list) {
					dout.writeUTF(transaction.toString());
				}
			  
				 
				 
                break;
			  }
				  
				
			  }
		
			
		}


 		
		

	}
}
