import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.github.javafaker.Faker;
import com.namics.commons.random.RandomData;

import rmit.sef.assignment1.collection.*;
import rmit.sef.assignment1.core.*;

public class DemoDataImporter {

	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	public static LocalDateTime getRandomDate(int rangeDay) {
		LocalDateTime now = LocalDateTime.now();
		int day = 60 * 60 * 24;
		return now.plusSeconds((long) RandomData.randomInteger(0, rangeDay * day));
	}

	public static void init() {
		Faker faker = new Faker();
		Random random = new Random();

		StudentCollection uc = new StudentCollection();
		EmployerCollection empContrl = new EmployerCollection();
		JobCollection jc = new JobCollection();
		
		for (int i = 0; i < 100; i++) {
			uc.addStudent(new Student(faker.name().username(), 
					"123456", 
					faker.name().username() + "@gmail.com",
					faker.name().firstName(), 
					faker.name().lastName(), 
					faker.educator().university(),
					faker.educator().course(), 
					(random.nextBoolean() ? Gender.Female : Gender.Male),
					getRandomNumberInRange(1990, 2022),
					new Availabilities(random.nextBoolean(), random.nextBoolean(), random.nextBoolean()),
					ApplicantStatus.Available));
			
			empContrl.addEmployer(new Employer(faker.name().username(), 
					"123456", 
					faker.name().username() + "@gmail.com",
					faker.name().firstName(), 
					faker.name().lastName(), 
					faker.company().name(),
					faker.company().profession())
					);
		}
		
		List<Employer> emps = empContrl.getAllEmployers();

		for (int i = 0; i < 100; i++) {
			
			jc.add(new Job(emps.get(random.nextInt(emps.size())).getEmail(),
					faker.job().title(),
					faker.job().position(),
					getRandomDate(getRandomNumberInRange(0, 30)),
					getRandomDate(getRandomNumberInRange(30, 90)),
					new Availabilities(random.nextBoolean(), random.nextBoolean(), random.nextBoolean())
					));
			
		}
	}
	
	

	public static void printAll() {
		StudentCollection uc = new StudentCollection();
		EmployerCollection empContrl = new EmployerCollection();
		JobCollection jc = new JobCollection();
		
		List<Employer> emps = empContrl.getAllEmployers();
		List<Student> students = uc.getAllStudents();
		List<Job> jobs = jc.getAllJobs();

		System.out.println("All employers:");
		for (Employer e : emps) {
			System.out.println(e);
		}
		System.out.println("\n\nAll Students");
		for (Student s : students) {
			System.out.println(s);
		}
		System.out.println("\n\nAll Jobs");
		for (Job j : jobs) {
			System.out.println(j);
		}
	}
}
