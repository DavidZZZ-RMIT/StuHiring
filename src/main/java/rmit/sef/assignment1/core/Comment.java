package rmit.sef.assignment1.core;

import java.time.LocalDate;

import org.apache.commons.lang3.RandomStringUtils;
import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;
import org.dizitart.no2.objects.Id;

import net.sf.json.JSONObject;

public class Comment implements Mappable {
	private LocalDate date = LocalDate.now();
	private String jobApplicationId;
	@Id
	private String cmtId = RandomStringUtils.randomAlphabetic(64);;
	private String parentId;
	private String author;
	private String content;
	private String dateStr;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getJobApplicationId() {
		return jobApplicationId;
	}

	public String getCmtId() {
		return cmtId;
	}

	public String getParentId() {
		return parentId;
	}

	public String getAuthor() {
		return author;
	}
	
	public Comment(String jobApplicationId, String parentId, String author, String content, String dateStr) {
		this.jobApplicationId = jobApplicationId;
		this.parentId = parentId;
		this.author = author;
		this.dateStr = dateStr;
		this.content = content;
	}

	@Override
	public Document write(NitriteMapper mapper) {
		Document document = new Document();
		document.put("jobApplicationId", jobApplicationId);
		document.put("parentId", parentId);
		document.put("author", author);
		document.put("content", content);
		document.put("cmtId", cmtId);
		document.put("dateStr", dateStr);
		return document;
	}

	@Override
	public void read(NitriteMapper mapper, Document document) {
		jobApplicationId = (String) document.get("jobApplicationId");
		parentId = (String) document.get("parentId");
		author = (String) document.get("author");		
		content = (String) document.get("content");		
		dateStr = (String) document.get("dateStr");		
		cmtId = (String) document.get("cmtId");	
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	
	public JSONObject toJson() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("dateStr", this.dateStr);
		jsonObj.put("jobApplicationId", this.jobApplicationId);
		jsonObj.put("parentId", this.parentId);
		jsonObj.put("author", this.author);
		jsonObj.put("content", this.content);
		jsonObj.put("cmtId", this.cmtId);
		return jsonObj;
	}


}
