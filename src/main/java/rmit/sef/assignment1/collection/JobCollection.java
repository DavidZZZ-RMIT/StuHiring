package rmit.sef.assignment1.collection;

import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import rmit.sef.assignment1.core.Job;
import rmit.sef.assignment1.db.DataKeeper;

public class JobCollection {
	private ObjectRepository<Job> repository;

	public JobCollection() {
		repository = DataKeeper.getInstance().getRepository(Job.class);
	}

	public boolean add(Job jb) {
		if (isExist(jb))
			return false;
		return repository.insert(jb).getAffectedCount() == 1;
	}
	
	public boolean isExist(Job jb) {
		return repository.find(ObjectFilters.eq("id", jb.getId())).size() > 0;
	}
	
	public void update(Job jb) {
		repository.update(jb);
	}

	public void remove(Job jb) {
		repository.remove(jb);
	}
}