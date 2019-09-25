package rmit.sef.assignment1.core;

import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.NitriteMapper;

import net.sf.json.JSONObject;

public class Student extends User {

	private String school;
	private String major;
	private Gender gender;
	private int graduateYear;
	private String description;
	private String employmentRecords;
	private String references;
	private String qualifications;
	private String licenses;
	private Availabilities availabilities;
	private ApplicantStatus status;

	public String toString() {
		return super.toString()+"School:" + school + " Major:" + major + " Gender:" + gender + " GraduateYear:" + graduateYear + "\n"
				+ "description:" + description + "\n" + "references:" + references + "\n" + "qualifications:"
				+ qualifications + "\n" + "Availabilities:" + availabilities + "\n" + "Status:" + status + "\n";
	}

	public JSONObject toJson() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("school", this.school);
		jsonObj.put("major", this.major);
		jsonObj.put("gender", this.gender);
		jsonObj.put("graduateYear", this.graduateYear);
		jsonObj.put("qualifications", this.qualifications);
		jsonObj.put("licenses", this.licenses);
		jsonObj.put("availabilities", this.availabilities);
		jsonObj.put("description", this.description);
		jsonObj.put("status", this.availabilities);
		return jsonObj;
	}

	@Override
	public Document write(NitriteMapper mapper) {
		Document document = super.write(mapper);
		document.put("school", school);
		document.put("major", major);
		document.put("description", description);
		document.put("gender", gender);
		document.put("graduateYear", graduateYear);
		document.put("employmentRecords", employmentRecords);
		document.put("references", references);
		document.put("qualifications", qualifications);
		document.put("licenses", licenses);
		document.put("availabilities", availabilities.toCodeString());
		document.put("status", status.toString());
		return document;
	}

	@Override
	public void read(NitriteMapper mapper, Document document) {
		super.read(mapper, document);
		school = (String) document.get("school");
		major = (String) document.get("major");
		description = (String) document.get("description");
		employmentRecords = (String) document.get("employmentRecords");
		references = (String) document.get("references");
		qualifications = (String) document.get("qualifications");
		licenses = (String) document.get("licenses");
		availabilities = Availabilities.fromCodeString((String) document.get("availabilities"));
		status = ApplicantStatus.fromString((String) document.get("status"));
		graduateYear = (int) document.get("graduateYear");
	}

	public Student(String userName, String pwd, String email, String fristName, String lastName) {
		super(userName, pwd, email, fristName, lastName, UserType.Student);
		availabilities = new Availabilities();
	}

	public Student(String userName, String pwd, String email, String fristName, String lastName, String school,
			String major, Gender gender, int graduateYear, Availabilities availabilities, ApplicantStatus status) {
		super(userName, pwd, email, fristName, lastName, UserType.Student);
		this.school = school;
		this.major = major;
		this.gender = gender;
		this.graduateYear = graduateYear;
		this.availabilities = availabilities;
		this.status = status;
	}

	public String getReferences() {
		return references;
	}

	public void setReferences(String references) {
		this.references = references;
	}

	public String getQualifications() {
		return qualifications;
	}

	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}

	public String getLicenses() {
		return licenses;
	}

	public void setLicenses(String licenses) {
		this.licenses = licenses;
	}

	public Availabilities getAvailabilities() {
		return availabilities;
	}

	public void setAvailabilities(Availabilities availabilities) {
		this.availabilities = availabilities;
	}

	public String getEmploymentRecords() {
		return employmentRecords;
	}

	public void setEmploymentRecords(String employmentRecords) {
		this.employmentRecords = employmentRecords;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getGraduateYear() {
		return graduateYear;
	}

	public void setGraduateYear(int graduateYear) {
		this.graduateYear = graduateYear;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ApplicantStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicantStatus status) {
		this.status = status;
	}

	public String getFullName() {
		return this.getFirstName() + " " + this.getLastName();
	}

}
