import static org.junit.Assert.*;
import org.junit.Test;
import net.sf.json.JSONObject;

public class JsonDataReactionDemoTest {

	@Test
	public void testJson() {
		JSONObject json = new JSONObject();
		json.put("json", "requestData");
		assertEquals(((String) WebTestHelper.request("http://localhost:4567/json", json).get("josn")), "testData");
	}
}
