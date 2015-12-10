package com.alex.gitbrancher.rest.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

public class JSONArrayResponseHandler extends JSONBaseResponseHandler<List<Object>> {

	@Override
	public List<Object> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		int status = response.getStatusLine().getStatusCode();
		List<Object> jsonObjs = new ArrayList<Object>();
		if (status >= 200 && status < 300) {
			HttpEntity entity = response.getEntity();
			// return entity != null ? EntityUtils.toString(entity) : null;

			String jsonString = entity != null ? EntityUtils.toString(entity) : null;
			JSONArray jsonArray = new JSONArray(jsonString);

			jsonObjs = toList(jsonArray);
			// for (int i = 0; i < jsonArray.length(); i++) {
			// JSONObject obj = jsonArray.getJSONObject(i);
			// jsonObjs.add(obj);
			// }
		} else {
			throw new ClientProtocolException("Unexpected response status: " + status);
		}
		return jsonObjs;
	}

}
