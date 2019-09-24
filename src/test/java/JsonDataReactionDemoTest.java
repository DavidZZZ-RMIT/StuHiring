import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import net.sf.json.JSONObject;

public class JsonDataReactionDemoTest {

//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}

	@Test
	public void givenUserExists_whenUserInformationIsRetrieved_thenRetrievedResourceIsCorrect() {
		HttpPost request = new HttpPost("http://localhost:4567/json");

		JSONObject json = new JSONObject();
		json.put("json", "requestData");

		StringEntity se;
		try {
			se = new StringEntity(json.toString());
			request.addHeader("Content-Type", "application/json");
			request.setEntity(se);

			// When
			HttpResponse response = HttpClientBuilder.create().build().execute(request);

			// Then
			JSONObject responseJson = JSONObject.fromObject(EntityUtils.toString(response.getEntity()));
			//System.out.print(responseJson);
			
			//check like this
			assertEquals(((String) responseJson.get("josn")),"testData");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		

	}
}
