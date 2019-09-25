import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public class WebTestHelper {
	public static JSONObject request(String url, JSONObject reqdata) {
		HttpPost request = new HttpPost(url);

		StringEntity se;
		try {
			se = new StringEntity(reqdata.toString());
			request.addHeader("Content-Type", "application/json");
			request.setEntity(se);

			HttpResponse response = HttpClientBuilder.create().build().execute(request);

			return JSONObject.fromObject(EntityUtils.toString(response.getEntity()));

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
		return null;
	}
}
