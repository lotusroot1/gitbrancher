package com.alex.gitbrancher.rest.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.alex.gitbrancher.rest.ResponseHandlerResultWrapper;

public class JSONObjectResponseHandler
		extends JSONBaseResponseHandler<ResponseHandlerResultWrapper<Map<String, Object>>> {

	@Override
	public ResponseHandlerResultWrapper<Map<String, Object>> handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		int status = response.getStatusLine().getStatusCode();
		if (status >= 200 && status < 300) {
			HttpEntity entity = response.getEntity();

			String jsonString = entity != null ? EntityUtils.toString(entity) : null;
			JSONObject jsonObject = new JSONObject(jsonString);

			map = toMap(jsonObject);
		} else {
			throw new ClientProtocolException("Unexpected response status: " + status);
		}

		return new ResponseHandlerResultWrapper<Map<String, Object>>(map, response);
	}
}
