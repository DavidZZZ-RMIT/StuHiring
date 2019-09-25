package rmit.sef.assignment1.core;

import org.apache.commons.codec.digest.DigestUtils;
import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;

public class User implements Mappable {

	private String userName;
	private String pwd;
	private String phone;
	private String email;
	private String lastName;
	private String firstName;
	private UserType userType;
	private boolean isBlackListed = false;

	public String toString() {
		return "[" + userType + "][" + (isBlackListed ? "X" : "O") + "][" + userName + "/" + email + "/" + phone + "]("
				+ firstName + " " + lastName + ")";
	}

	public User(String userName, String pwd, String email, String fristName, String lastName, UserType userType) {
		super();
		this.userName = userName;
		this.pwd = DigestUtils.md5Hex(pwd);
		this.email = email;
		this.lastName = lastName;
		this.firstName = fristName;
		this.userType = userType;
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Document write(NitriteMapper mapper) {
		Document document = new Document();
		document.put("userName", userName);
		document.put("pwd", pwd);
		document.put("phone", phone);
		document.put("email", email);
		document.put("lastName", lastName);
		document.put("fristName", firstName);
		document.put("userType", userType.toString());
		document.put("isBlackListed", isBlackListed);

		return document;
	}

	@Override
	public void read(NitriteMapper mapper, Document document) {
		userName = (String) document.get("userName");
		pwd = (String) document.get("pwd");
		phone = (String) document.get("phone");
		email = (String) document.get("email");
		lastName = (String) document.get("lastName");
		firstName = (String) document.get("fristName");
		isBlackListed = (Boolean) document.get("isBlackListed");
		userType = UserType.getUserType((String) document.get("userType"));
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public boolean checkPwd(String pwd) {
		return this.pwd.equals(DigestUtils.md5Hex(pwd));
	}

	public void setPwd(String pwd) {
		this.pwd = DigestUtils.md5Hex(pwd);
	}

	public String getEmail() {
		return email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isBlackListed() {
		return isBlackListed;
	}

	public void setBlackListed(boolean isBlackListed) {
		this.isBlackListed = isBlackListed;
	}

}
