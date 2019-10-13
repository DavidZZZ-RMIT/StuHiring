package rmit.sef.assignment1.core;

import org.apache.commons.lang3.RandomStringUtils;
import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;
import org.dizitart.no2.objects.Id;

import net.sf.json.JSONObject;

public class CV implements Mappable {
	@Id
	private String id = RandomStringUtils.randomAlphabetic(64);
	private String studentId;
	private String title;
	private String content;
	
	public CV(String studentId, String title, String content) {
		super();
		this.studentId = studentId;
		this.title = title;
		this.content = content;
	}
	
	@Override
	public Document write(NitriteMapper mapper) {
		Document document = new Document();
		document.put("content", content);
		document.put("title", title);
		document.put("id", id);
		document.put("studentId", studentId);
		return document;
	}
	@Override
	public void read(NitriteMapper mapper, Document document) {
		content = (String) document.get("content");
		title = (String) document.get("title");
		id = (String) document.get("id");		
		studentId = (String) document.get("studentId");		
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStudentId() {
		return studentId;
	}

	public String getId() {
		return id;
	}
	
	public JSONObject toJson() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("title", this.title);
		jsonObj.put("id", this.id);
		jsonObj.put("studentId", this.studentId);
		jsonObj.put("content", this.content);
		return jsonObj;
	}
}
