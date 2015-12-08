package com.alex.gitbrancher.helper;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class JSONObjectResponseHandler implements ResponseHandler<JSONObject> {

	@Override
	public JSONObject handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		int status = response.getStatusLine().getStatusCode();
		JSONObject jsonObject = null;
		if (status >= 200 && status < 300) {
			HttpEntity entity = response.getEntity();

			String jsonString = entity != null ? EntityUtils.toString(entity) : null;
			jsonObject = new JSONObject(jsonString);
		} else {
			throw new ClientProtocolException("Unexpected response status: " + status);
		}
		return jsonObject;
	}

}
