import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.github.javafaker.Faker;

import net.sf.json.JSONObject;
import rmit.sef.assignment1.collection.EmployerCollection;
import rmit.sef.assignment1.collection.JobCollection;
import rmit.sef.assignment1.collection.StudentCollection;
import rmit.sef.assignment1.core.ApplicantStatus;
import rmit.sef.assignment1.core.Availabilities;
import rmit.sef.assignment1.core.Employer;
import rmit.sef.assignment1.core.Gender;
import rmit.sef.assignment1.core.Job;
import rmit.sef.assignment1.core.Student;

public class ApplyJobTestCase {

	//@Before
	public void setUp() throws Exception {
		Faker faker = new Faker();
		Random random = new Random();

		StudentCollection uc = new StudentCollection();
		EmployerCollection empContrl = new EmployerCollection();
		JobCollection jc = new JobCollection();

		uc.addStudent(new Student("David", "123456", "zzz@gmail.com", faker.name().firstName(), faker.name().lastName(),
				faker.educator().university(), faker.educator().course(),
				(random.nextBoolean() ? Gender.Female : Gender.Male),
				DemoDataImporter.getRandomNumberInRange(1990, 2022), 
				new Availabilities(false, true, true), // Fulltime, internship, parttime
				ApplicantStatus.Available));

		empContrl.addEmployer(new Employer("RMITConnect", "123456", "rmit_connect@gmail.com", faker.name().firstName(),
				faker.name().lastName(), faker.company().name(), faker.company().profession()));

		empContrl.addEmployer(new Employer("ANZ", "123456", "anz@gmail.com", faker.name().firstName(),
				faker.name().lastName(), faker.company().name(), faker.company().profession()));

		
		jc.add(new Job("rmit_connect@gmail.com", "IT support", faker.job().position(),
				DemoDataImporter.getRandomDate(DemoDataImporter.getRandomNumberInRange(0, 30)),
				DemoDataImporter.getRandomDate(DemoDataImporter.getRandomNumberInRange(30, 90)),
				new Availabilities(true, false, false)));// Fulltime, internship, parttime

		jc.add(new Job("rmit_connect@gmail.com", "Java developer", faker.job().position(),
				DemoDataImporter.getRandomDate(DemoDataImporter.getRandomNumberInRange(0, 30)),
				DemoDataImporter.getRandomDate(DemoDataImporter.getRandomNumberInRange(30, 90)),
				new Availabilities(false, true, true)));// Fulltime, internship, parttime
		
		jc.add(new Job("anz@gmail.com", "C/C++ developer", faker.job().position(),
				DemoDataImporter.getRandomDate(DemoDataImporter.getRandomNumberInRange(0, 30)),
				DemoDataImporter.getRandomDate(DemoDataImporter.getRandomNumberInRange(30, 90)),
				new Availabilities(false, true, true)));// Fulltime, internship, parttime
		
		jc.add(new Job("anz@gmail.com", "Java Tester", faker.job().position(),
				DemoDataImporter.getRandomDate(DemoDataImporter.getRandomNumberInRange(0, 30)),
				DemoDataImporter.getRandomDate(DemoDataImporter.getRandomNumberInRange(30, 90)),
				new Availabilities(true, true, true)));// Fulltime, internship, parttime
	}

	@Test
	public void applyFullTimeJob() {
		JSONObject reqjson = new JSONObject();

		// login as a student
		reqjson.put("email", "zzz@gmail.com");
		reqjson.put("pwd", "123456");

		JSONObject respjson = WebTestHelper.request("http://localhost:4567/loginAsStudent", reqjson);

		assertEquals(((String) respjson.get("result")), "success");

		reqjson.clear();

		// find a IT job
		reqjson.put("email", "zzz@gmail.com");
		reqjson.put("keywords", "IT");

		respjson = WebTestHelper.request("http://localhost:4567/findJobs", reqjson);

		Iterator<String> it = respjson.keys();
		while (it.hasNext()) {
			String title = it.next();
			assertTrue(title.contains("IT"));
			if (title.contains("IT")) {
				reqjson.clear();
				reqjson.put("email", "zzz@gmail.com");
				reqjson.put("jobid", (String) ((JSONObject) respjson.get(title)).get("jobID"));
				reqjson.put("employer", (String) ((JSONObject) respjson.get(title)).get("employer"));
				
				respjson = WebTestHelper.request("http://localhost:4567/applyJob", reqjson);
				assertEquals(((String) respjson.get("result")), "success");
			}
		}
	}
	
	@Test
	public void applyPartTimeJob() {
		JSONObject reqjson = new JSONObject();

		// login as a student, he can works for parttiime and internship
		reqjson.put("email", "zzz@gmail.com");
		reqjson.put("pwd", "123456");

		JSONObject respjson = WebTestHelper.request("http://localhost:4567/loginAsStudent", reqjson);

		assertEquals(((String) respjson.get("result")), "success");

		reqjson.clear();

		// find a Java job which is parttime
		reqjson.put("email", "zzz@gmail.com");
		reqjson.put("keywords", "IT");

		respjson = WebTestHelper.request("http://localhost:4567/findJobs", reqjson);

		Iterator<String> it = respjson.keys();
		while (it.hasNext()) {
			String title = it.next();
			assertTrue(title.contains("Java"));
			if (title.contains("Java")) {
				reqjson.clear();
				reqjson.put("email", "zzz@gmail.com");
				reqjson.put("jobid", (String) ((JSONObject) respjson.get(title)).get("jobID"));
				reqjson.put("employer", (String) ((JSONObject) respjson.get(title)).get("employer"));
				
				respjson = WebTestHelper.request("http://localhost:4567/applyJob", reqjson);
				assertEquals(((String) respjson.get("result")), "failed");
			}
		}
	}

}
