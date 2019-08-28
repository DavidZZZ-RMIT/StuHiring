package rmit.sef.assignment1.core;

public abstract class User {

	protected String userName;
	protected String pwd;
	protected String email;
	protected String lastName;
	protected String firstName;
	protected String id;
	
	public boolean login(String userName, String password) {
		this.userName = userName;
		this.pwd = password;
		return true;
	}
	
	public boolean register(String userName,String pwd,String email) {
		this.userName = userName;
		this.pwd = pwd;
		this.email = email;
		return true;
	}
	
	public void updatePersonalProfile(String lastName,String firstName) {
		this.lastName = lastName;
		this.firstName = firstName;
	}
}
