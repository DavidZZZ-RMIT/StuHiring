package rmit.sef.assignment1.collection;

import java.util.List;

import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import net.sf.json.JSONObject;
import rmit.sef.assignment1.core.Employer;
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
	
	public Job getJob(String id) {
		return repository.find(ObjectFilters.eq("id", id)).firstOrDefault();
	}

	public void update(Job jb) {
		repository.update(jb);
	}

	public void remove(Job jb) {
		repository.remove(jb);
	}

	public List<Job> findJobs(String keywords) {
		return repository.find(
				ObjectFilters.or(ObjectFilters.text("description", keywords), ObjectFilters.text("title", keywords)))
				.toList();
	}
	
	public List<Job> getAllJobs() {
		return repository.find().toList();
	}
	
	public void removeAll() {
		repository.remove(ObjectFilters.ALL);
	}
	
}
