import static org.junit.Assert.*;

import org.junit.Test;

import rmit.sef.assignment1.core.Employer;
import rmit.sef.assignment1.core.Student;
import rmit.sef.assignment1.core.User;

public class DemoFunctionUnitTest {

	@Test
	public void testLogin() {
		User u1 = new Student();
		assertTrue(u1.login("davidzzz", "123456"));
		//fail("Not yet implemented");
		
		User u2 = new Employer();
		assertTrue(u1.login("adamzzz", "123456"));
	}
	
	@Test
	public void testRegister() {
		//fail("Not yet implemented");
		User u1 = new Student();
		assertTrue(u1.register("user1", "123456", "user1.demo@gmail.com"));
		
		User u2 = new Employer();
		assertTrue(u2.register("user1", "123456", "user1.demo@gmail.com"));
	}

}
