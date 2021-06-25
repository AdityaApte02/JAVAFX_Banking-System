package backend;

import java.sql.Date;

public class Transaction {

	private int transaction_id;
	private double Total_Balance;
	private String name;
	private Date transaction_time;
	
	
	public Transaction(int transaction_id, double amount, String name, Date transaction_time) {
		this.transaction_id = transaction_id;
		this.Total_Balance = amount;
		this.name = name;
		this.transaction_time = transaction_time;
	}
	
	public int getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}
	public double getAmount() {
		return Total_Balance;
	}
	public void setAmount(double amount) {
		this.Total_Balance = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getTransaction_time() {
		return transaction_time;
	}
	public void setTransaction_time(Date transaction_time) {
		this.transaction_time = transaction_time;
	}

	@Override
	public String toString() {
		return "Transaction [transaction_id=" + transaction_id + ", Total_Balance=" + Total_Balance + ", name=" + name
				+ ", transaction_time=" + transaction_time + "]";
	}


	
	
}