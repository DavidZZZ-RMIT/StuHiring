package rmit.sef.assignment1.core;

import org.apache.commons.lang3.RandomStringUtils;
import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;
import org.dizitart.no2.objects.Id;

import net.sf.json.JSONObject;

public class JobApplication implements Mappable {
	@Id
	private String id = RandomStringUtils.randomAlphabetic(64);
	private String applicant;
	private String CVID;
	private String employer;
	private String jobID;
	private String message;
	private JobApplicationStatus status;

	public JSONObject toJson() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("applicant", this.applicant);
		jsonObj.put("jobID", this.jobID);
		jsonObj.put("id", this.id);
		jsonObj.put("status", this.status);
		jsonObj.put("employer", this.employer);
		jsonObj.put("CVID", this.CVID);
		jsonObj.put("message", this.message);
		return jsonObj;
	}

	public JobApplicationStatus getStatus() {
		return status;
	}

	public JobApplication(String applicant,String cvID, String jobID, String employer,String msg) {
		this.applicant = applicant;
		this.jobID = jobID;
		this.CVID = cvID;
		this.employer = employer;
		this.message = msg;
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
	
	public String getCVID() {
		return CVID;
	}

	@Override
	public Document write(NitriteMapper mapper) {
		Document document = new Document();
		document.put("id", id);
		document.put("applicant", applicant);
		document.put("employer", employer);
		document.put("jobID", jobID);
		document.put("CVID", CVID);
		document.put("status", status);
		document.put("message", message);
		return document;
	}

	@Override
	public void read(NitriteMapper mapper, Document document) {
		id = (String) document.get("id");
		applicant = (String) document.get("applicant");
		employer = (String) document.get("employer");
		jobID = (String) document.get("jobID");
		CVID = (String) document.get("CVID");
		message = (String) document.get("message");
		status = (JobApplicationStatus) document.get("status");
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
