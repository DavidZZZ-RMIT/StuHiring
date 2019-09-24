package rmit.sef.assignment1.collection;

import java.util.List;

import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import rmit.sef.assignment1.core.JobApplication;
import rmit.sef.assignment1.core.JobApplicationStatus;
import rmit.sef.assignment1.db.DataKeeper;

public class JobApplicationCollection {
	private ObjectRepository<JobApplication> repository;

	public JobApplicationCollection() {
		repository = DataKeeper.getInstance().getRepository(JobApplication.class);
	}

	public boolean add(JobApplication ja) {
		if (isExist(ja))
			return false;
		return repository.insert(ja).getAffectedCount() == 1;
	}

	public boolean isExist(JobApplication ja) {
		return repository.find(ObjectFilters.eq("id", ja.getId())).size() > 0;
	}

	public void update(JobApplication ja) {
		repository.update(ja);
	}

	public void remove(JobApplication ja) {
		repository.remove(ja);
	}

	public List<JobApplication> getJobApplicationsByApplicant(String applicant) {
		return repository.find(ObjectFilters.eq("applicant", applicant)).toList();
	}

	public List<JobApplication> getJobApplicationsByApplicant(String applicant, JobApplicationStatus status) {
		return repository
				.find(ObjectFilters.and(ObjectFilters.eq("applicant", applicant), ObjectFilters.eq("status", status)))
				.toList();
	}

	public List<JobApplication> getJobApplicationsByJobID(String jobID) {
		return repository.find(ObjectFilters.eq("jobID", jobID)).toList();
	}

	public List<JobApplication> getJobApplicationsByJobID(String jobID, JobApplicationStatus status) {
		return repository.find(ObjectFilters.and(ObjectFilters.eq("jobID", jobID), ObjectFilters.eq("status", status)))
				.toList();
	}
}
