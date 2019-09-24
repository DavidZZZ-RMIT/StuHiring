package rmit.sef.assignment1.core;

import org.apache.commons.lang3.RandomStringUtils;
import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;

public class JobApplication implements Mappable {
	private String id = RandomStringUtils.randomAlphabetic(64);
	private String applicant;
	private String jobID;
	private JobApplicationStatus status;

	public JobApplicationStatus getStatus() {
		return status;
	}

	public JobApplication(String applicant, String jobID) {
		this.applicant = applicant;
		this.jobID = jobID;
		this.status = JobApplicationStatus.Requesting;
	}

	public void setStatus(JobApplicationStatus status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public String getApplicant() {
		return applicant;
	}

	public String getJobID() {
		return jobID;
	}

	@Override
	public Document write(NitriteMapper mapper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void read(NitriteMapper mapper, Document document) {
		// TODO Auto-generated method stub

	}

}
