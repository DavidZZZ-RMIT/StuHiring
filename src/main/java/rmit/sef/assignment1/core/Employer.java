package rmit.sef.assignment1.core;

public class Employer extends User {
	private String companyName;
	private String companyDescription;

	public Employer(String userName, String pwd, String email,String fristName, String lastName) {
		super(userName, pwd, email, fristName, lastName, UserType.Employer);
		// TODO Auto-generated constructor stub
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}

	public boolean postJob(String jobDescription) {
		return true;
	}

}
