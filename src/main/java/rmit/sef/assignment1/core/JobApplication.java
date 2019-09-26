package rmit.sef.assignment1.core;

import org.apache.commons.lang3.RandomStringUtils;
import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;

import net.sf.json.JSONObject;

public class JobApplication implements Mappable {
	private String id = RandomStringUtils.randomAlphabetic(64);
	private String applicant;
	private String employer;
	private String jobID;
	private JobApplicationStatus status;

	public JSONObject toJson() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("applicant", this.applicant);
		jsonObj.put("jobID", this.jobID);
		jsonObj.put("status", this.status);
		return jsonObj;
	}

	public JobApplicationStatus getStatus() {
		return status;
	}

	public JobApplication(String applicant, String jobID, String employer) {
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

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
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
