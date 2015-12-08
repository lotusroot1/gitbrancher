package com.alex.gitbrancher.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONArrayResponseHandler implements ResponseHandler<List<JSONObject>> {

	@Override
	public List<JSONObject> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		int status = response.getStatusLine().getStatusCode();
		List<JSONObject> jsonObjs = new ArrayList<JSONObject>();
		if (status >= 200 && status < 300) {
			HttpEntity entity = response.getEntity();
			// return entity != null ? EntityUtils.toString(entity) : null;

			String jsonString = entity != null ? EntityUtils.toString(entity) : null;
			JSONArray jsonArray = new JSONArray(jsonString);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				jsonObjs.add(obj);
			}
		} else {
			throw new ClientProtocolException("Unexpected response status: " + status);
		}
		return jsonObjs;
	}

}
