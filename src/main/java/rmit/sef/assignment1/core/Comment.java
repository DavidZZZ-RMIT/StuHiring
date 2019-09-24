package rmit.sef.assignment1.core;

import java.time.LocalDate;

import org.apache.commons.lang3.RandomStringUtils;
import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;

public class Comment implements Mappable {

	private LocalDate date = LocalDate.now();
	private String jobApplicationId;
	private String id = RandomStringUtils.randomAlphabetic(64);;
	private String parentId;
	private String author;
	private String content;
	
	public Comment(String jobApplicationId, String parentId, String author, String content) {
		this.jobApplicationId = jobApplicationId;
		this.parentId = parentId;
		this.author = author;
		this.content = content;
	}

	@Override
	public Document write(NitriteMapper mapper) {
		Document document = new Document();
		document.put("jobApplicationId", jobApplicationId);
		document.put("parentId", parentId);
		document.put("author", author);
		document.put("content", content);
		document.put("id", id);
		document.put("date", util.FORMATTER.format(date));

		return document;
	}

	@Override
	public void read(NitriteMapper mapper, Document document) {
		jobApplicationId = (String) document.get("jobApplicationId");
		parentId = (String) document.get("parentId");
		author = (String) document.get("author");		
		content = (String) document.get("content");		
		id = (String) document.get("id");	
		date = LocalDate.parse((String) document.get("date"), util.FORMATTER);
	}

}