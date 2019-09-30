import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Before;
import org.junit.Test;

import rmit.sef.assignment1.collection.*;
import rmit.sef.assignment1.core.*;

public class ModelLayerTestCase {
	
	StudentCollection StuContrl = new StudentCollection();
	EmployerCollection empContrl = new EmployerCollection();
	JobCollection jobContrl = new JobCollection();

	@Before
	public void setUp() throws Exception {
		StuContrl.removeAll();
		empContrl.removeAll();
		jobContrl.removeAll();
	}

	@Test
	public void testAddStudent() {
		StuContrl.addStudent(new Student(
				"David", 
				"123456", 
				"zzz@gmail.com", 
				"David", 
				"Andrew",
				"RMIT", 
				"IT",
				Gender.Male,
				2020, 
				new Availabilities(false, true, true), // Fulltime, internship, parttime
				ApplicantStatus.Available));
		
		
		Student s = StuContrl.getStudent("zzz@gmail.com");
		
		assertTrue(s.getFirstName().equals("David"));
		assertTrue(s.getLastName().equals("Andrew"));
		assertTrue(s.getGender() == Gender.Male);
		assertTrue(s.getMajor().equals("IT"));
		assertTrue(s.getSchool().equals("RMIT"));
		assertTrue(s.getGraduateYear() == 2020);
	}
	
	@Test
	public void testStudentPassword() {
		StuContrl.addStudent(new Student(
				"David", 
				"123456", 
				"zzz@gmail.com", 
				"David", 
				"Andrew",
				"RMIT", 
				"IT",
				Gender.Male,
				2020, 
				new Availabilities(false, true, true), // Fulltime, internship, parttime
				ApplicantStatus.Available));
		
		
		Student s = StuContrl.getStudent("zzz@gmail.com");
		
		assertTrue(s.getPwd().equals("123456"));
		//assertFalse(s.getPwd().equals("123456"));
		//assertTrue(s.checkPwd("123456"));
	}
	
	@Test
	public void testAddEmployer() {
		empContrl.addEmployer(new Employer(
				"ANZ", 
				"123456", 
				"anz@gmail.com", 
				"Jean",
				"John", 
				"ANZ Australia",
				"A bank of Australia"));
		
		Employer e = empContrl.getEmployer("anz@gmail.com");
		
		assertTrue(e.getCompanyName().equals("ANZ Australia"));
		assertTrue(e.getCompanyDescription().equals("A bank of Australia"));
		assertTrue(e.getFirstName().equals("Jean"));
		assertTrue(e.getLastName().equals("John"));
		assertTrue(e.checkPwd("123456"));
	}
	
	@Test
	public void testPostJob() {
		Job j = new Job(
				"anz@gmail.com",
				"IT support", 
				"This is Job need a IT major student for us",
				LocalDateTime.of(2019,Month.SEPTEMBER,30,12,1),
				LocalDateTime.of(2019,Month.DECEMBER,30,12,1),
				new Availabilities(true, false, false));// Fulltime, internship, parttime
		
		jobContrl.add(j);
	
		Job j2 = jobContrl.getJob(j.getId());
		
		assertTrue(j.getDescription().equals(j2.getDescription()));
		assertTrue(j.getDueDate().equals(j2.getDueDate()));
		assertTrue(j.getEmployer().equals(j2.getEmployer()));
		assertTrue(j.getTitle().equals(j2.getTitle()));
	
	}
}
