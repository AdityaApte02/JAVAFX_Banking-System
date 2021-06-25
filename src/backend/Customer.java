package backend;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable{
	private String name;
    private String phone;
    private String address;
    private int age;

    private static final long SerialVersionUID=1L;

    public Customer(String name,String phone,String address,int age) {
        this.name = name;
        this.phone=phone;
        this.address=address;
        this.age=age;

        
    }

    

    public String getName() {
        return name;
    }
    
    
    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setName(String name) {
		this.name = name;
	}



	@Override
	public String toString() {
		return "Customer [name=" + name + ", phone=" + phone + ", address=" + address + ", age=" + age + "]";
	}

		
	
	
	

}
