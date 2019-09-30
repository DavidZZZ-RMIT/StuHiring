package rmit.sef.assignment1.collection;

import java.util.List;

import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import rmit.sef.assignment1.core.JobCategory;
import rmit.sef.assignment1.db.DataKeeper;

public class JobCategoryCollection {
	private ObjectRepository<JobCategory> repository;

	public JobCategoryCollection() {
		repository = DataKeeper.getInstance().getRepository(JobCategory.class);
	}
	
	public boolean add(JobCategory jb) {
		if (isExist(jb))
			return false;
		return repository.insert(jb).getAffectedCount() == 1;
	}
	
	public boolean isExist(JobCategory jb) {
		return repository.find(ObjectFilters.eq("title", jb.getTitle())).size() > 0;
	
	}
	
	public void remove(JobCategory jb) {
		repository.remove(jb);
	}
	
	public List<JobCategory> all() {
		return repository.find().toList();
	}
	
	public void removeAll() {
		repository.remove(ObjectFilters.ALL);
	}
}
