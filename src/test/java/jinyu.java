import static org.junit.Assert.*;

import org.junit.Test;

import rmit.sef.assignment1.core.Employer;
import rmit.sef.assignment1.core.Job;
import rmit.sef.assignment1.core.Student;
import rmit.sef.assignment1.core.User;

public class jinyu {

	@Test
	public void testUpdatePersonalProfile() {
		User u1=new Student();
		assertTrue(u1.updatePersonalProfile("YU", "JIN"));
	}
	@Test
	public void testUpdateJobDescription() {
		Job job =new Job();
		job.setJobDescription("this is demo description");
		assertEquals("this is demo description", job.getJobDesceription());
	}
	@Test
	public void testUpdaeEmployerId() {
		Job job =new Job();
		job.setEmployerID("EMP001");
		assertEquals("EMP001",job.getEmployerID());
	}
	@Test
	public void testUpdateCompanyName() {
		Employer employer= new Employer();
		employer.setCompanyName("HUAWEI");
		assertEquals("HUAWEI",employer.getCompanyName());
	}
	

}
