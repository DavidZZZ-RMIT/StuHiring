package rmit.sef.assignment1.core;
import java.util.ArrayList;

public class StuHiring {
	private ArrayList<User> allUser;
	
	
	public StuHiring() {
		allUser= new ArrayList<User>();
	}
	
	public void addUser(User user) {
		int i = 0;
		allUser.add(i, user);
		
	}
	
	public void getUser(User u) {
		int i;
		for (i = 0; i < allUser.size(); i++) {
			String user = allUser.get(i).getId();
			// Getting the ID of every user object in the ArrayList allUser.
			if (user.equals(u)) {
				break;
				// Once the user in the ArrayList is the user inputed by administer, break.
			}
	    }
	}
	
	

}

