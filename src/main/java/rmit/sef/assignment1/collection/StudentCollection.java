package rmit.sef.assignment1.collection;

import java.util.List;

import org.dizitart.no2.IndexOptions;
import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import rmit.sef.assignment1.core.Student;
import rmit.sef.assignment1.db.DataKeeper;

public class StudentCollection {

	private ObjectRepository<Student> repository;

	public StudentCollection() {
		repository = DataKeeper.getInstance().getRepository(Student.class);
		if (!repository.hasIndex("email"))
			repository.createIndex("email", IndexOptions.indexOptions(IndexType.Unique));
		if (!repository.hasIndex("description"))
			repository.createIndex("description", IndexOptions.indexOptions(IndexType.Fulltext));
		if (!repository.hasIndex("employmentrecords"))
			repository.createIndex("employmentrecords", IndexOptions.indexOptions(IndexType.Fulltext));
		if (!repository.hasIndex("references"))
			repository.createIndex("references", IndexOptions.indexOptions(IndexType.Fulltext));
		if (!repository.hasIndex("qualifications"))
			repository.createIndex("qualifications", IndexOptions.indexOptions(IndexType.Fulltext));
		if (!repository.hasIndex("licenses"))
			repository.createIndex("licenses", IndexOptions.indexOptions(IndexType.Fulltext));
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
		repository.update(ObjectFilters.eq("email", user.getEmail()), user);
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

	public void removeAll() {
		repository.remove(ObjectFilters.ALL);
	}
}
