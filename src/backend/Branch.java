package backend;

import java.util.ArrayList;

public class Branch {

	private int branch_id;
	private String branchName;
    

    public Branch(String BranchName)
    {
          this.branchName=BranchName;
     
    }
   
    public int getBranch_id() {
		return branch_id;
	}


	public void setBranch_id(int branch_id) {
		this.branch_id = branch_id;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}



}

