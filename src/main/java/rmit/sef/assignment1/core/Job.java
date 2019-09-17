package rmit.sef.assignment1.core;

import java.time.LocalDate;

public class Job {
	private String EmployerID;
	private String jobDescription;
	private LocalDate dueDate;
	private LocalDate publicDate;
	private JobApplication[] applications;
	
	public String getJobDesceription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription=jobDescription;
	}
	public String getEmployerID() {
		return EmployerID;
	}
	public void setEmployerID(String EmployerID) {
		this.EmployerID=EmployerID;
	}
	
	
	
    
}


