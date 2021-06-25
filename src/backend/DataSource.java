package backend;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataSource {
	

	public static final String TABLE_BRANCH="branches";
	public static final String TABLE_CUSTOMER="customers";
	public static final String TABLE_TRANSACTION="transactions";
	
	public static final String CREATE_TABLE_BRANCH="CREATE TABLE IF NOT EXISTS "+TABLE_BRANCH+
			"(branch_id INTEGER not NULL AUTO_INCREMENT, "+
			"branch_name VARCHAR(30), "+
			"PRIMARY KEY (branch_id) )";
	
	public static final String CREATE_TABLE_CUSTOMER="CREATE TABLE IF NOT EXISTS "+TABLE_CUSTOMER+
			"(customer_id INTEGER not null AUTO_INCREMENT, "+
			"customer_name VARCHAR(30), "+
			"customer_phone VARCHAR(12), "+
			"customer_address VARCHAR(50), "+
			"customer_age INTEGER, "+
			"branch_id INTEGER, "+
			"PRIMARY KEY ( customer_id ), "
			+ "FOREIGN KEY(branch_id) REFERENCES branches(branch_id) ON DELETE CASCADE)";
	
	public static final String CREATE_TABLE_TRANSACTION="CREATE TABLE IF NOT EXISTS "+TABLE_TRANSACTION+
			"(transaction_id INTEGER not NULL AUTO_INCREMENT, "+
			"customer_name VARCHAR(30), "+
			"Total_Balance DECIMAL DEFAULT 0 , "+
			"transaction_time DATETIME, "+
			"branch_id INTEGER, "+
			"PRIMARY KEY ( transaction_id ), "
			+ "FOREIGN KEY(branch_id) REFERENCES branches(branch_id) ON DELETE CASCADE)";
	
	public static final String QUERY_BRANCHNAME="SELECT "+TABLE_BRANCH+".branch_name FROM "+
	TABLE_BRANCH+" WHERE "+TABLE_BRANCH+".branch_name= ?";
	
	public static final String QUERY_CUSTOMERNAME="SELECT "+TABLE_CUSTOMER+".customer_phone, "+
	TABLE_CUSTOMER+".customer_name, " +
	TABLE_CUSTOMER+".customer_address, "+
	TABLE_CUSTOMER+".customer_age FROM "+
	TABLE_CUSTOMER+" WHERE "+TABLE_CUSTOMER+".customer_phone= ?";
	
	public static final String ADD_BRANCH="INSERT INTO "+TABLE_BRANCH+"(branch_name) "+
			" VALUES( ? )";
	
	public static final String ADD_CUSTOMER="INSERT INTO "+TABLE_CUSTOMER+"(customer_name,"
	
			+ "customer_phone, customer_address, customer_age, branch_id)"+" VALUES(?, ?, ?, ?, ?);";
	public static final String ADD_TRANSACTION="INSERT INTO "+TABLE_TRANSACTION+"(customer_name,"
			+ "Total_Balance, transaction_time,  branch_id)"+" VALUES(?, ?, CURRENT_TIMESTAMP, ?)";
	
	public static final String QUERY_BALANCE="SELECT "+TABLE_TRANSACTION+".Total_Balance FROM "+
	TABLE_TRANSACTION+" WHERE "+TABLE_TRANSACTION+".customer_name= ?";
	
	public static final String DELETE_CUSTOMER="DELETE FROM "+TABLE_CUSTOMER+" WHERE "+
	TABLE_CUSTOMER+".customer_phone= ?";
	
	public static final String QUERY_TRANSACTIONS="SELECT "+TABLE_TRANSACTION+".transaction_id, "+
	TABLE_TRANSACTION+".customer_name, "+
	TABLE_TRANSACTION+".Total_Balance, "+
	TABLE_TRANSACTION+".transaction_time FROM "+
	TABLE_TRANSACTION+" WHERE "+TABLE_TRANSACTION+".customer_name= ?";
	
	
	private Connection conn;
	private Statement create_branch_table;
	private Statement create_customer_table;
	private Statement create_transaction_table;
	private PreparedStatement insert_into_branch;
	private PreparedStatement query_branchname;
	private PreparedStatement insert_into_customer;
	private PreparedStatement query_customer;
	private PreparedStatement insert_into_transaction;
	private PreparedStatement query_balance;
	private PreparedStatement delete_customer;
	private PreparedStatement query_transactions;
	private static final String username="root";      //Enter your username and password
	private static final String password="aditya02";
	
	
	public boolean open() {
		try {
			
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Banking", username, password );
			create_branch_table=conn.createStatement();
			insert_into_branch=conn.prepareStatement(ADD_BRANCH);
			query_branchname=conn.prepareStatement(QUERY_BRANCHNAME);
			create_customer_table=conn.createStatement();
			insert_into_customer=conn.prepareStatement(ADD_CUSTOMER);
			query_customer=conn.prepareStatement(QUERY_CUSTOMERNAME);
			create_transaction_table=conn.createStatement();
			insert_into_transaction=conn.prepareStatement(ADD_TRANSACTION);
			query_balance=conn.prepareStatement(QUERY_BALANCE);
			delete_customer=conn.prepareStatement(DELETE_CUSTOMER);
			query_transactions=conn.prepareStatement(QUERY_TRANSACTIONS);
			return true;
		}catch(SQLException e) {
			System.out.println("SQL Exception: "+e.getMessage());
			return false;
		}
	}
	
	public void close() {
		try {
			if(conn!=null) {
				if(create_branch_table!=null) {
					create_branch_table.close();
				}
				
				if(create_customer_table!=null) {
					create_customer_table.close();
				}
				
				
				if(create_transaction_table!=null) {
					create_transaction_table.close();
				}
				
				
				if(insert_into_branch!=null) {
					insert_into_branch.close();
				}
				
				if(insert_into_customer!=null) {
					insert_into_customer.close();
				}
				
				if(query_branchname!=null) {
					query_branchname.close();
				}
				
				if(query_customer!=null) {
					query_customer.close();
				}
				
				if(query_balance!=null) {
					query_balance.close();
				}
				
				if(delete_customer!=null) {
					delete_customer.close();
				}
				
				if(query_transactions!=null) {
					query_transactions.close();
				}
				conn.close();
			}
		}catch(SQLException e) {
			System.out.println("SQL Exception: "+e.getMessage());
		}
	}
	
	public boolean createBranchTable() {
		try {
				
			create_branch_table.executeUpdate(CREATE_TABLE_BRANCH);
			return true;
			
		}catch(SQLException e) {
			System.out.println("SQL Exception: "+e.getMessage());
			return false;
		}
	}
	
	public boolean createCustomerTable() {
		try {
				create_customer_table.executeUpdate(CREATE_TABLE_CUSTOMER);
				return true;
				
		}catch(SQLException e) {
			System.out.println("SQL Exception: "+e.getMessage());
			return false;
		}
	}
	
	public boolean createTransactionTable() {
		try {
				create_transaction_table.executeUpdate(CREATE_TABLE_TRANSACTION);
				return true;
				
		}catch(SQLException e) {
			System.out.println("SQL Exception: "+e.getMessage());
			return false;
		}
	}
	
	public int addBranch(String branchName) {
		try {
			
			conn.setAutoCommit(false);
			String branch=queryBranchName(branchName);
			
			if(branch==null) {
				insert_into_branch.setString(1, branchName);
												
				int count=insert_into_branch.executeUpdate();
				conn.commit();
				
				return count;
			}
			else if(branch!=null) {
				System.out.println("Branch Already exists!!");
				return -1;
			}
			
			
			
		}catch(SQLException e) {
			System.out.println("SQL Exception: "+e.getMessage());
		try {
				
				conn.rollback();
				System.out.println("The data was rolled back");
				
			}catch(SQLException e2)
			{
				System.out.println("Could not rollback the data "+e2.getMessage());
			}
			
		}finally {
			try {
				conn.setAutoCommit(true);
				System.out.println("Auto-Commit set to true");
				}catch(SQLException e)
				{
					System.out.println("Can't set auto-commit to true");
				}
		}
		
		return -1;
	}
	
	public int addCustomer(String name, String phone, String address, int age, int branch_id){
		try {
			
			conn.setAutoCommit(false);
			Customer customer=queryCustomer(phone);
			
			if(customer==null) {
				insert_into_customer.setString(1, name);
				insert_into_customer.setString(2, phone);
				insert_into_customer.setString(3, address);
				insert_into_customer.setInt(4, age);
				insert_into_customer.setInt(5, branch_id);
				
			
				int count=insert_into_customer.executeUpdate();
				conn.commit();
				
				return count;
			}
			else if(customer!=null) {
				System.out.println("Customer Already exists!!");
				return -1;
			}
			
		}catch(SQLException e) {
			System.out.println("SQL Exception: "+e.getMessage());
		try {
				
				conn.rollback();
				System.out.println("The data was rolled back");
				
			}catch(SQLException e2)
			{
				System.out.println("Could not rollback the data "+e2.getMessage());
			}
			
		}finally {
			try {
				conn.setAutoCommit(true);
				System.out.println("Auto-Commit set to true");
				}catch(SQLException e)
				{
					System.out.println("Can't set auto-commit to true");
				}
		}
		
		return -1;
		
	}
	
	public String queryBranchName(String branchName) {
		try {
			
			query_branchname.setString(1, branchName);
			ResultSet resultSet=query_branchname.executeQuery();
			
			String BranchName="";
			while(resultSet.next()) {
				 BranchName=resultSet.getString(1);
			}
			if(!BranchName.equals("")) {
				return BranchName;
			}
			return null;
			
		}catch(SQLException e) {
			System.out.println("SQL Exception: "+e.getMessage());
			return null;
		}
		
	}
	
	public Customer queryCustomer(String customer_phone) {
	try {
			
			query_customer.setString(1, customer_phone);
			ResultSet resultSet=query_customer.executeQuery();
			
			Customer customer=null;
			while(resultSet.next()) {
				
				String phone=resultSet.getString(1);
				 String name=resultSet.getString(2);
				 String address=resultSet.getString(3);
				 int age=resultSet.getInt(4);
				
				  customer=new Customer(name, phone, address, age);
			}
			
				return customer;
			
		}catch(SQLException e) {
			System.out.println("SQL Exception: "+e.getMessage());
			return null;
		}
	}
	
	public double queryBalance(String name) {
		
		try {
			query_balance.setString(1, name);
			ResultSet resultSet=query_balance.executeQuery();
			
			
			double balance=0;
			while(resultSet.next()) {
				 balance=resultSet.getDouble(1);
			}
			
			return balance;
			
						
		}catch(SQLException e) {
			System.out.println("SQL Exception: "+e.getMessage());
			return -1;
		}
	}
	
	public synchronized int addAmount(String phone,double amount, int branch_id) {
		
		try {
			conn.setAutoCommit(false);
			Customer customer=queryCustomer(phone);
			
			if(customer==null){
				System.out.println("Customer doesn't exist");
				return -1;
			}
			
			double balance=queryBalance(customer.getName());
			
			System.out.println("Prev Balance: "+balance);
			
			balance=balance+amount;
			
			System.out.println("Later Balance: "+balance);
			
			insert_into_transaction.setString(1, customer.getName());
			insert_into_transaction.setDouble(2, balance);
			insert_into_transaction.setInt(3, branch_id);
			
			int count=insert_into_transaction.executeUpdate();
			conn.commit();
			
			return count;
			
			
			
		}catch(SQLException e) {
			System.out.println("SQL Exception: "+e.getMessage());
			try {
				
				conn.rollback();
				System.out.println("The data was rolled back");
				
			}catch(SQLException e2)
			{
				System.out.println("Could not rollback the data "+e2.getMessage());
			}
			
		}finally {
			try {
				conn.setAutoCommit(true);
				System.out.println("Auto-Commit set to true");
				}catch(SQLException e)
				{
					System.out.println("Can't set auto-commit to true");
				}
		}
		
		return -1;
		}
	
public  synchronized int WithdrawAmount(String phone,double amount, int branch_id) {
		
		try {
			conn.setAutoCommit(false);
			Customer customer=queryCustomer(phone);
			
			if(customer==null){
				System.out.println("Customer doesn't exist");
				return -1;
			}
			
			double balance=queryBalance(customer.getName());
			
			if(balance<0) {
				System.out.println("Zero Balance \n Can't withdraw");
				return -1;
			}
			
			if(amount > balance) {
				System.out.println("Not enough Balance");
				return -1;
			}
			
			balance=balance-amount;
			
			insert_into_transaction.setString(1, customer.getName());
			insert_into_transaction.setDouble(2, balance);
			insert_into_transaction.setInt(3, branch_id);
			
			int count=insert_into_transaction.executeUpdate();
			conn.commit();
			
			return count;
			
			
			
		}catch(SQLException e) {
			System.out.println("SQL Exception: "+e.getMessage());
			try {
				
				conn.rollback();
				System.out.println("The data was rolled back");
				
			}catch(SQLException e2)
			{
				System.out.println("Could not rollback the data "+e2.getMessage());
			}
			
		}finally {
			try {
				conn.setAutoCommit(true);
				System.out.println("Auto-Commit set to true");
				}catch(SQLException e)
				{
					System.out.println("Can't set auto-commit to true");
				}
		}
		
		return -1;
		}
		

		public int deleteCusomer(String phone) {
			
			try {
				conn.setAutoCommit(false);
				Customer customer=queryCustomer(phone);
				
				if(customer==null){
					System.out.println("Customer doesn't exist");
					return -1;
				}
				
				delete_customer.setString(1, phone);
				int count=delete_customer.executeUpdate();
				
				conn.commit();
				return count;
				
			}catch(SQLException e) {
				System.out.println("SQL Exception: "+e.getMessage());
				try {
					
					conn.rollback();
					System.out.println("The data was rolled back");
					
				}catch(SQLException e2)
				{
					System.out.println("Could not rollback the data "+e2.getMessage());
				}
			}finally {
				try {
					conn.setAutoCommit(true);
					System.out.println("Auto-Commit set to true");
					}catch(SQLException e)
					{
						System.out.println("Can't set auto-commit to true");
					}
			}
			return -1;
		}
		
		public List<Transaction> viewTransactions(String phone) {
			
			try {
					Customer customer=queryCustomer(phone);
					
					if(customer==null) {
						System.out.println("Customer doesn't exist");
						return null;
					}
					
					query_transactions.setString(1, customer.getName());
					
					ResultSet resultSet=query_transactions.executeQuery();
					
					
					List<Transaction> trans_list=new ArrayList<>();
					while(resultSet.next()) {
						int trans_id=resultSet.getInt(1);
						String name=resultSet.getString(2);
						double balance=resultSet.getDouble(3);
						Date trans_time=resultSet.getDate(4);
						
						Transaction transaction=new Transaction(trans_id, balance, name, trans_time);
						trans_list.add(transaction);
						
					}
					
					return trans_list;
					
					
					
			}catch(SQLException e) {
				System.out.println("SQL Exception: "+e.getMessage());
				return null;
			}
		}
		
	}


