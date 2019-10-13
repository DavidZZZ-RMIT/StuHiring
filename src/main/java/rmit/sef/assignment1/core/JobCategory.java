package rmit.sef.assignment1.core;

import org.apache.commons.lang3.RandomStringUtils;
import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;
import org.dizitart.no2.objects.Id;

public class JobCategory  implements Mappable {
	@Id
	private String id = RandomStringUtils.randomAlphabetic(64);;
	private String title;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public JobCategory(String title) {
		this.title = title;
	}
	@Override
	public Document write(NitriteMapper mapper) {
		Document document = new Document();
		document.put("title", title);
		document.put("id", id);
		return document;
	}

	@Override
	public void read(NitriteMapper mapper, Document document) {
		title = (String) document.get("title");
		id = (String) document.get("id");
	}
	
}
