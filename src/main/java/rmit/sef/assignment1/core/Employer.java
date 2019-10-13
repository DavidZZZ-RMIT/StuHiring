package rmit.sef.assignment1.core;

import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.NitriteMapper;
import org.dizitart.no2.objects.Id;

import net.sf.json.JSONObject;

public class Employer extends User {
	private String companyName;
	private String companyDescription;

	public Employer(String userName, String pwd, String email,String fristName, String lastName) {
		super(userName, pwd, email, fristName, lastName, UserType.Employer);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Document write(NitriteMapper mapper) {
		Document document = super.write(mapper);
		document.put("companyName", companyName);
		document.put("companyDescription", companyDescription);
		return document;
	}

	@Override
	public void read(NitriteMapper mapper, Document document) {
		super.read(mapper, document);
		companyName = (String) document.get("companyName");
		companyDescription = (String) document.get("companyDescription");
	}
	public Employer(String userName, String pwd, String email, String fristName, String lastName,
			String companyName, String companyDescription) {
		super(userName, pwd, email, fristName, lastName, UserType.Employer);
		this.companyName = companyName;
		this.companyDescription = companyDescription;
	}
	
	public JSONObject toJson() {
		JSONObject jsonObj = super.toJson();
		jsonObj.put("companyName", this.companyName);
		jsonObj.put("companyDescription", this.companyDescription);
		return jsonObj;
	}
	
	public String toString() {
		return super.toString()+" :: ["+companyName+"]:"+companyDescription;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}

	public boolean postJob(String jobDescription) {
		return true;
	}

}
