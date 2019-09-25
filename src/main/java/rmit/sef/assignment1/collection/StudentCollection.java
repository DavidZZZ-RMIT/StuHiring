package rmit.sef.assignment1.collection;

import java.util.List;

import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import rmit.sef.assignment1.core.Employer;
import rmit.sef.assignment1.core.Job;
import rmit.sef.assignment1.core.Student;
import rmit.sef.assignment1.db.DataKeeper;

public class StudentCollection {

	private ObjectRepository<Student> repository;

	public StudentCollection() {
		repository = DataKeeper.getInstance().getRepository(Student.class);
	}

	public boolean addStudent(Student student) {
		if (isStudentExist(student))
			return false;
		return repository.insert(student).getAffectedCount() == 1;
	}

	public boolean isStudentExist(Student user) {
		return repository.find(ObjectFilters.eq("email", user.getEmail())).size() > 0;
	}

	public boolean isStudentExist(String email) {
		return repository.find(ObjectFilters.eq("email", email)).size() > 0;
	}

	public boolean addStudents(Student[] users) {
		return repository.insert(users).getAffectedCount() == users.length;
	}

	public Student getStudent(String emial) {
		return repository.find(ObjectFilters.eq("email", emial)).firstOrDefault();
	}

	public void updateStudent(Student user) {
		repository.update(user);
	}

	public void removeStudent(Student user) {
		repository.remove(user);
	}

	public List<Student> findApplicants(String keywords) {
		return repository.find(ObjectFilters.or(ObjectFilters.text("description", keywords),
				ObjectFilters.text("school", keywords), ObjectFilters.text("major", keywords))).toList();
	}
	
	public List<Student> getAllStudents() {
		return repository.find().toList();
	}
}
