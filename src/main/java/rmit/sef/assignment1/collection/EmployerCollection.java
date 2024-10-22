package rmit.sef.assignment1.collection;

import java.util.List;

import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import rmit.sef.assignment1.core.Employer;
import rmit.sef.assignment1.db.DataKeeper;

public class EmployerCollection {
	
	private ObjectRepository<Employer> repository;

	public EmployerCollection() {
		repository = DataKeeper.getInstance().getRepository(Employer.class);
	}

	public boolean addEmployer(Employer employer) {
		if (isEmployerExist(employer))
			return false;
		return repository.insert(employer).getAffectedCount() == 1;
	}

	public boolean isEmployerExist(Employer emp) {
		return repository.find(ObjectFilters.eq("email", emp.getEmail())).size() > 0;
	}

	public boolean isEmployerExist(String email) {
		return repository.find(ObjectFilters.eq("email", email)).size() > 0;
	}
	
	public Employer getEmployer(String email) {
		return repository.find(ObjectFilters.eq("email", email)).firstOrDefault();
	}

	public void updateEmployer(Employer employer) {
		repository.update(ObjectFilters.eq("email", employer.getEmail()),employer);
	}

	public void removeEmployer(Employer employer) {
		repository.remove(employer);
	}
	
	public List<Employer> getAllEmployers() {
		return repository.find().toList();
	}
	
	public void removeAll() {
		repository.remove(ObjectFilters.ALL);
	}

}
