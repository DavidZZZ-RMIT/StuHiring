package rmit.sef.assignment1.core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.RandomStringUtils;
import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;

import net.sf.json.JSONObject;

public class Job implements Mappable {
	private String employer;
	private String title;
	private String id = RandomStringUtils.randomAlphabetic(64);;
	private String description;
	private LocalDate dueDate;
	private LocalDate publicDate;
	private Availabilities availabilities;

	public Job(String employer, String title, String description, LocalDate dueDate, LocalDate publicDate) {
		this.employer = employer;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.publicDate = publicDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getPublicDate() {
		return publicDate;
	}

	public void setPublicDate(LocalDate publicDate) {
		this.publicDate = publicDate;
	}

	public String getEmployer() {
		return employer;
	}

	public String getId() {
		return id;
	}

	@Override
	public Document write(NitriteMapper mapper) {
		Document document = new Document();
		document.put("employer", employer);
		document.put("title", title);
		document.put("id", id);
		document.put("description", description);
		document.put("dueDate", util.FORMATTER.format(dueDate));
		document.put("publicDate", util.FORMATTER.format(publicDate));
		document.put("availabilities", availabilities.toCodeString());
		return document;
	}

	@Override
	public void read(NitriteMapper mapper, Document document) {
		employer = (String) document.get("employer");
		title = (String) document.get("title");
		id = (String) document.get("id");
		description = (String) document.get("description");
		dueDate = LocalDate.parse((String) document.get("dueDate"), util.FORMATTER);
		publicDate = LocalDate.parse((String) document.get("publicDate"), util.FORMATTER);
		availabilities = Availabilities.fromCodeString((String) document.get("availabilities"));
	}

	public JSONObject toJson() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("title", this.title);
		jsonObj.put("description", this.description);
		jsonObj.put("dueDate", this.dueDate);
		jsonObj.put("availabilities", this.availabilities);
		return jsonObj;
	}
}
