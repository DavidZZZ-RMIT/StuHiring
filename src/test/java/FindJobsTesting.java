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

public class FindJobsTesting {

	//@Before
	public void setUp() throws Exception {
		StudentCollection stuContrl = new StudentCollection();
		EmployerCollection empContrl = new EmployerCollection();
		JobCollection jobContrl = new JobCollection();

		Faker faker = new Faker();
		Random random = new Random();

		stuContrl.addStudent(new Student(faker.name().username(), "123456", faker.name().username() + "@gmail.com",
				faker.name().firstName(), faker.name().lastName(), faker.educator().university(),
				"education", (random.nextBoolean() ? Gender.Female : Gender.Male),
				DemoDataImporter.getRandomNumberInRange(1990, 2022),
				new Availabilities(random.nextBoolean(), random.nextBoolean(), random.nextBoolean()),
				ApplicantStatus.Available));
		
		
		stuContrl.addStudent(new Student(faker.name().username(), "123456", faker.name().username() + "@gmail.com",
				faker.name().firstName(), faker.name().lastName(), faker.educator().university(),
				faker.educator().course(), (random.nextBoolean() ? Gender.Female : Gender.Male),
				DemoDataImporter.getRandomNumberInRange(1990, 2022),
				new Availabilities(random.nextBoolean(), random.nextBoolean(), random.nextBoolean()),
				ApplicantStatus.Available));
		
		stuContrl.addStudent(new Student(faker.name().username(), "123456", faker.name().username() + "@gmail.com",
				faker.name().firstName(), faker.name().lastName(), faker.educator().university(),
				faker.educator().course(), (random.nextBoolean() ? Gender.Female : Gender.Male),
				DemoDataImporter.getRandomNumberInRange(1990, 2022),
				new Availabilities(random.nextBoolean(), random.nextBoolean(), random.nextBoolean()),
				ApplicantStatus.Available));
		
		stuContrl.addStudent(new Student(faker.name().username(), "123456", faker.name().username() + "@gmail.com",
				faker.name().firstName(), faker.name().lastName(), faker.educator().university(),
				faker.educator().course(), (random.nextBoolean() ? Gender.Female : Gender.Male),
				DemoDataImporter.getRandomNumberInRange(1990, 2022),
				new Availabilities(random.nextBoolean(), random.nextBoolean(), random.nextBoolean()),
				ApplicantStatus.Available));
		

		empContrl.addEmployer(new Employer(faker.name().username(), "123456", faker.name().username() + "@gmail.com",
				faker.name().firstName(), faker.name().lastName(), "Thai style","Massage"));
		empContrl.addEmployer(new Employer(faker.name().username(), "123456", faker.name().username() + "@gmail.com",
				faker.name().firstName(), faker.name().lastName(), "Smile Thai","Restaurant"));
		empContrl.addEmployer(new Employer(faker.name().username(), "123456", faker.name().username() + "@gmail.com",
				faker.name().firstName(), faker.name().lastName(), faker.company().name(),
				faker.company().profession()));
		empContrl.addEmployer(new Employer(faker.name().username(), "123456", faker.name().username() + "@gmail.com",
				faker.name().firstName(), faker.name().lastName(), faker.company().name(),
				"Chinese Education"));
		empContrl.addEmployer(new Employer(faker.name().username(), "123456", faker.name().username() + "@gmail.com",
				faker.name().firstName(), faker.name().lastName(), faker.company().name(),
				"Russion Education"));
		empContrl.addEmployer(new Employer(faker.name().username(), "123456", faker.name().username() + "@gmail.com",
				faker.name().firstName(), faker.name().lastName(), faker.company().name(),
				"Spanish Education"));
		empContrl.addEmployer(new Employer(faker.name().username(), "123456", faker.name().username() + "@gmail.com",
				faker.name().firstName(), faker.name().lastName(), faker.company().name(),
				faker.company().profession()));
		empContrl.addEmployer(new Employer(faker.name().username(), "123456", faker.name().username() + "@gmail.com",
				faker.name().firstName(), faker.name().lastName(), faker.company().name(),
				faker.company().profession()));

		jobContrl.add(new Job("", faker.job().title(), faker.job().position(),
				DemoDataImporter.getRandomDate(DemoDataImporter.getRandomNumberInRange(0, 30)),
				DemoDataImporter.getRandomDate(DemoDataImporter.getRandomNumberInRange(30, 90)),
				new Availabilities(random.nextBoolean(), random.nextBoolean(), random.nextBoolean())));
		jobContrl.add(new Job("", faker.job().title(), faker.job().position(),
				DemoDataImporter.getRandomDate(DemoDataImporter.getRandomNumberInRange(0, 30)),
				DemoDataImporter.getRandomDate(DemoDataImporter.getRandomNumberInRange(30, 90)),
				new Availabilities(random.nextBoolean(), random.nextBoolean(), random.nextBoolean())));
		jobContrl.add(new Job("", faker.job().title(), faker.job().position(),
				DemoDataImporter.getRandomDate(DemoDataImporter.getRandomNumberInRange(0, 30)),
				DemoDataImporter.getRandomDate(DemoDataImporter.getRandomNumberInRange(30, 90)),
				new Availabilities(random.nextBoolean(), random.nextBoolean(), random.nextBoolean())));
		jobContrl.add(new Job("", faker.job().title(), faker.job().position(),
				DemoDataImporter.getRandomDate(DemoDataImporter.getRandomNumberInRange(0, 30)),
				DemoDataImporter.getRandomDate(DemoDataImporter.getRandomNumberInRange(30, 90)),
				new Availabilities(random.nextBoolean(), random.nextBoolean(), random.nextBoolean())));
		jobContrl.add(new Job("", faker.job().title(), faker.job().position(),
				DemoDataImporter.getRandomDate(DemoDataImporter.getRandomNumberInRange(0, 30)),
				DemoDataImporter.getRandomDate(DemoDataImporter.getRandomNumberInRange(30, 90)),
				new Availabilities(random.nextBoolean(), random.nextBoolean(), random.nextBoolean())));
	}

	@Test
	public void LoginAsStudent() {
		JSONObject json = new JSONObject();
		json.put("email", "xxx");
		json.put("pwd", "xxx");

		JSONObject result = WebTestHelper.request("http://localhost:4567/loginAsStudent", json);

		assertTrue(result.has("result") || ((String) result.get("result")).equals("succss"));

	}

	@Test
	public void findJobsWithoutLogin() {
		JSONObject json = new JSONObject();
		String keywords = "Education";
		json.put("keywords", "Education");
		JSONObject result = WebTestHelper.request("http://localhost:4567/findJobs", json);

		Iterator<String> it = result.keys();
		while (it.hasNext()) {
			String title = it.next();
			assertTrue(title.contains(keywords)
					|| ((String) ((JSONObject) result.get(title)).get("description")).contains(keywords));
		}
	}

	@Test
	public void findJobsAccordingToPreference() {
		JSONObject json = new JSONObject();
		String keywords = "Thai";
		json.put("keywords", "Thai");
		JSONObject result = WebTestHelper.request("http://localhost:4567/findJobs", json);
		Iterator<String> it=result.keys();
		while (it.hasNext()) {
			String title = it.next();
			assertTrue(title.contains(keywords)
					|| ((String) ((JSONObject) result.get(title)).get("description")).contains(keywords));
		}
	}

	@Test
	public void findAllOfAvailableJobsAfterLogIn() {
		JSONObject json = new JSONObject();
		json.put("email", "xxx");
		json.put("pwd", "xxx");

		JSONObject result = WebTestHelper.request("http://localhost:4567/loginAsStudent", json);

		assertTrue(result.has("result") || ((String) result.get("result")).equals("succss"));
		String keywords=" ";
		json.put("keywords", " ");
		Iterator<String> it=result.keys();
		while (it.hasNext()) {
			String title = it.next();
			assertTrue(title.contains(keywords)
					|| ((String) ((JSONObject) result.get(title)).get("description")).contains(keywords));
		}
	}

}